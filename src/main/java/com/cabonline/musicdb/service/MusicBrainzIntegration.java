package com.cabonline.musicdb.service;

import com.cabonline.musicdb.dto.MusicBrainzResponseDTO;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;

/**
 * Created by s_lor_000 on 11/24/2015.
 */
public interface MusicBrainzIntegration {
    MusicBrainzResponseDTO query(String mbId);
}
