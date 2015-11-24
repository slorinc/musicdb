package com.cabonline.musicdb.service;

import com.cabonline.musicdb.dto.MusicDBResponseDTOBuilder;
import com.cabonline.musicdb.dto.MusicDBResponseDTO;
import com.cabonline.musicdb.vo.ArtistData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
        ArtistData artistData = musicBrainzIntegration.requestArtistData(mbId);
        MusicDBResponseDTO musicDBResponseDTO =  new MusicDBResponseDTOBuilder().setMbId(mbId).setDescription(artistData.getName()).createMusicDBResponseDTO();
        return musicDBResponseDTO;
    }
}
