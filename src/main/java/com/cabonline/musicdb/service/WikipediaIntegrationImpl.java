package com.cabonline.musicdb.service;

import com.cabonline.musicdb.dto.WikipediaResponseDTO;
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
public class WikipediaIntegrationImpl implements WikipediaIntegration {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate customRestTemplate;

    @Value("${wikipedia.extract.endpoint}")
    private String wikipediaExtractEndpoint;

    private static final Logger LOG = LoggerFactory.getLogger(WikipediaIntegrationImpl.class);

    @Override
    public WikipediaResponseDTO query(String title) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);
        WikipediaResponseDTO wikipediaResponseDTO = new WikipediaResponseDTO();

        String url = null;

        try {
            Instant before = Instant.now();
            String wikipediaJSON = customRestTemplate
                    .exchange(wikipediaExtractEndpoint + title, HttpMethod.GET, entity, String.class).getBody();
            Instant after = Instant.now();
            url = wikipediaExtractEndpoint + title;
            LOG.debug("Request to {} took {} ", new Object[]{url, Duration.between(before, after)});
            wikipediaResponseDTO = objectMapper.readValue(wikipediaJSON, WikipediaResponseDTO.class);

        } catch (HttpStatusCodeException ex) {
            LOG.error(ErrorMessages.HTTP_RESPONSE_ERROR+" "+url, ex);
            wikipediaResponseDTO.setError(ErrorCodes.WIKIPEDIA_GENERIC, String.format(ErrorMessages.HTTP_RESPONSE_ERROR, ex.getStatusCode()));
        } catch (IOException e) {
            LOG.error(ErrorMessages.JSON_PARSING_ERROR, e);
            wikipediaResponseDTO.setError(ErrorCodes.WIKIPEDIA_GENERIC, ErrorMessages.JSON_PARSING_ERROR);
        }
        return wikipediaResponseDTO;
    }

}
