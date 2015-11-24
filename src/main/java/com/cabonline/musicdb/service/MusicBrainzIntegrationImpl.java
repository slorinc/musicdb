package com.cabonline.musicdb.service;

import com.cabonline.musicdb.vo.ArtistData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
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

    @Override
    public ArtistData requestArtistData(String mbId) throws IOException, HttpClientErrorException {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ArtistData artistData = null;
        try {
            String json = customRestTemplate
                    .exchange(musicbrainzArtistEndpoint
                            + mbId + "?fmt=json&inc=url-rels+release-groups", HttpMethod.GET, entity, String.class).getBody();
            artistData = objectMapper.readValue(json, ArtistData.class);
        } catch (HttpStatusCodeException ex) {
            //TODO handle error
        }

        return artistData;
    }
}
