package com.cabonline.musicdb.service;

import com.cabonline.musicdb.dto.*;
import com.cabonline.musicdb.vo.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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


    @Override
    public MusicDBResponseDTO query(String mbId) throws IOException {
        List<RestResponse> errors = new LinkedList<>();
        MusicBrainzResponseDTO musicBrainzResponseDTO = musicBrainzIntegration.query(mbId);
        String[] urlBits = musicBrainzResponseDTO.getRelationList().stream().filter(p -> p.getType().equals("wikipedia")).findFirst().get().getUrl().getResource().split("/");
        WikipediaResponseDTO wikipediaResponseDTO = wikipediaIntegration.query(urlBits[urlBits.length-1]);
        List<Album> albums = new ArrayList<>();
        musicBrainzResponseDTO.getReleasesList().stream().forEach(p -> albums.add(new Album(p.getTitle(),p.getMdId(),null)));
        MusicDBResponseDTO musicDBResponseDTO =  new MusicDBResponseDTOBuilder().setMbId(mbId).setDescription(wikipediaResponseDTO.getExtract()).setAlbums(albums).createMusicDBResponseDTO();
        return musicDBResponseDTO;
    }
}
