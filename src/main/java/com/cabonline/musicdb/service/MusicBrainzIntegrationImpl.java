package com.cabonline.musicdb.service;

import com.cabonline.musicdb.dto.MusicBrainzResponseDTO;
import com.cabonline.musicdb.error.ErrorCodes;
import com.cabonline.musicdb.error.ErrorMessages;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

/**
 * Created by s_lor_000 on 11/24/2015.
 */
@Service
public class MusicBrainzIntegrationImpl implements MusicBrainzIntegration {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate customRestTemplate;

    @Value("${musicbrainz.artist.endpoint}")
    private String musicbrainzArtistEndpoint;

    private static final Logger LOG = LoggerFactory.getLogger(MusicBrainzIntegrationImpl.class);

    @Override
    public MusicBrainzResponseDTO query(String mbId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);
        MusicBrainzResponseDTO musicBrainzResponseDTO = new MusicBrainzResponseDTO();
        String url = null;
        try {
            Instant before = Instant.now();
            url = musicbrainzArtistEndpoint
                    + mbId + "?fmt=json&inc=url-rels+release-groups";
            String artistDataJSON = customRestTemplate
                    .exchange(url, HttpMethod.GET, entity, String.class).getBody();
            Instant after = Instant.now();
            LOG.debug("Request to {} took {} ", new Object[]{url, Duration.between(before, after)});
            musicBrainzResponseDTO = objectMapper.readValue(artistDataJSON, MusicBrainzResponseDTO.class);
        } catch (HttpStatusCodeException ex) {
            LOG.error(ErrorMessages.HTTP_RESPONSE_ERROR+" "+url, ex);
            musicBrainzResponseDTO.setError(ErrorCodes.MUSIC_BRAINZ_GENERIC, ErrorMessages.HTTP_RESPONSE_ERROR);
        } catch (IOException e) {
            LOG.error(ErrorMessages.JSON_PARSING_ERROR, e);
            musicBrainzResponseDTO.setError(ErrorCodes.MUSIC_BRAINZ_GENERIC, ErrorMessages.JSON_PARSING_ERROR);
        }

        return musicBrainzResponseDTO;
    }
}
