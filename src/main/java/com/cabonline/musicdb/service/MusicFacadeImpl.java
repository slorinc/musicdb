package com.cabonline.musicdb.service;

import com.cabonline.musicdb.dto.*;
import com.cabonline.musicdb.dto.builder.ErrorDTOBuilder;
import com.cabonline.musicdb.dto.builder.MusicDBResponseDTOBuilder;
import com.cabonline.musicdb.error.ErrorCodes;
import com.cabonline.musicdb.error.ErrorMessages;
import com.cabonline.musicdb.vo.Album;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by s_lor_000 on 11/24/2015.
 */
@Service
public class MusicFacadeImpl implements MusicFacade {

    @Autowired
    private MusicBrainzIntegration musicBrainzIntegration;

    @Autowired
    private WikipediaIntegration wikipediaIntegration;

    @Autowired
    private CoverArtArchiveIntegration coverArtArchiveIntegration;

    private static final Logger LOG = LoggerFactory.getLogger(MusicFacadeImpl.class);


    @Override
    public MusicDBResponseDTO query(String mbId) {
        List<ErrorDTO> errors = new LinkedList<>();
        // musicbrainz
        MusicBrainzResponseDTO musicBrainzResponseDTO = musicBrainzIntegration.query(mbId);

        addErrors(errors, musicBrainzResponseDTO);

        // error is musicbrains response
        if (!errors.isEmpty()) {
            return new MusicDBResponseDTOBuilder()
                    .setMbId(mbId)
                    .setErrors(errors)
                    .createMusicDBResponseDTO();
        }

        // coverart async
        Map<String, Future<CoverArtArchiveResponseDTO>> futureMap = getStringFutureMap(musicBrainzResponseDTO);

        // wikipedia
        String extract = getWikipediaExtract(errors, musicBrainzResponseDTO);


        waitForThreadsToFinish(errors, futureMap);

        // build result
        return buildMusicDBResponseDTO(mbId, errors, musicBrainzResponseDTO, futureMap, extract);
    }

    private Map<String, Future<CoverArtArchiveResponseDTO>> getStringFutureMap(MusicBrainzResponseDTO musicBrainzResponseDTO) {
        Map<String, Future<CoverArtArchiveResponseDTO>> futureMap = new HashMap<>();
        musicBrainzResponseDTO.getReleasesList().stream()
                .filter(release -> (release.getPrimaryType() != null && release.getPrimaryType().equalsIgnoreCase("album")))
                .forEach(album -> futureMap.put(album.getMdId(), coverArtArchiveIntegration.query(album.getMdId())));
        return futureMap;
    }

    private String getWikipediaExtract(List<ErrorDTO> errors, MusicBrainzResponseDTO musicBrainzResponseDTO) {
        String description = null;
        Optional<RelationDTO> wikipedia = musicBrainzResponseDTO.getRelationList().stream().filter(relation -> relation.getType().equalsIgnoreCase("wikipedia")).findFirst();
        // has wiki page info
        if(wikipedia.isPresent()){
            String[] urlBits = wikipedia.get().getUrl().getResource().split("/");
            WikipediaResponseDTO wikipediaResponseDTO = wikipediaIntegration.query(urlBits[urlBits.length - 1]);
            // could find the wiki page
            if (wikipediaResponseDTO.getQuery()!=null){
                description = wikipediaResponseDTO.getQuery().getPages().values().stream().findFirst().get().get("extract");
            }
            addErrors(errors, wikipediaResponseDTO);
        }
        return description;
    }

    private void waitForThreadsToFinish(List<ErrorDTO> errors, Map<String, Future<CoverArtArchiveResponseDTO>> futureList) {
        while (futureList.values().stream().anyMatch(future -> !future.isDone())) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                errors.add(new ErrorDTOBuilder().setErrorCode(ErrorCodes.MUSICDB_GENERIC).setErrorMessage(e.getMessage()).createErrorDTO());
                LOG.error(ErrorMessages.GENERIC_ERROR, e);
            }
        }
    }

    private <T extends ErrorDTO> void addErrors(List<ErrorDTO> errors, T dto) {
        if (dto.getErrorCode() != null) {
            errors.add(new ErrorDTOBuilder().setErrorCode(dto.getErrorCode()).setErrorMessage(dto.getErrorMessage()).createErrorDTO());
        }
    }

    private MusicDBResponseDTO buildMusicDBResponseDTO(String mbId, List<ErrorDTO> errors, MusicBrainzResponseDTO musicBrainzResponseDTO, Map<String, Future<CoverArtArchiveResponseDTO>> futureMap, String extract) {
        List<Album> albums = new ArrayList<>();
        musicBrainzResponseDTO.getReleasesList().stream()
                .filter(release -> (release.getPrimaryType() != null && release.getPrimaryType().equalsIgnoreCase("album")))
                .forEach(p -> {
                            String imageUrl = null;
                            try {
                                CoverArtArchiveResponseDTO coverArtArchiveResponseDTO = futureMap.get(p.getMdId()).get();

                                addErrors(errors, coverArtArchiveResponseDTO);

                                List<CoverArtImagesDTO> images = coverArtArchiveResponseDTO.getImages();

                                if (images == null || images.get(0) == null) {
                                    LOG.warn(ErrorMessages.MISSING_IMAGE + " for mdib : {}", p.getMdId());
                                } else {
                                    imageUrl = images.get(0).getImage();
                                }

                            } catch (InterruptedException e) {
                                errors.add(new ErrorDTOBuilder().setErrorCode(ErrorCodes.MUSICDB_GENERIC).setErrorMessage(e.getMessage()).createErrorDTO());
                                LOG.error(ErrorMessages.GENERIC_ERROR, e);
                            } catch (ExecutionException ex) {
                                errors.add(new ErrorDTOBuilder().setErrorCode(ErrorCodes.MUSICDB_GENERIC).setErrorMessage(ex.getMessage()).createErrorDTO());
                                LOG.error(ErrorMessages.GENERIC_ERROR, ex);
                            }

                            albums.add(new Album(p.getTitle(), p.getMdId(), imageUrl));
                        }
                );

        MusicDBResponseDTO musicDBResponseDTO = new MusicDBResponseDTOBuilder()
                .setMbId(mbId)
                .setDescription(extract)
                .setAlbums(albums.isEmpty() ? null : albums)
                .setErrors(errors.isEmpty() ? null : errors)
                .createMusicDBResponseDTO();

        if (!errors.isEmpty()) {
            musicDBResponseDTO.setErrorCode(ErrorCodes.MUSICDB_GENERIC);
            musicDBResponseDTO.setErrorMessage(ErrorMessages.ERRORS_DURING_EXCECUTION);
        }

        return musicDBResponseDTO;
    }

}
