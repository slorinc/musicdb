package com.cabonline.musicdb.service;

import com.cabonline.musicdb.dto.WikipediaResponseDTO;
import com.cabonline.musicdb.error.ErrorCodes;
import com.cabonline.musicdb.error.ErrorMessages;
import com.fasterxml.jackson.core.type.TypeReference;
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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

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
        try {
            Instant before = Instant.now();
            String wikipediaJSON = customRestTemplate
                    .exchange(wikipediaExtractEndpoint + title, HttpMethod.GET, entity, String.class).getBody();
            Instant after = Instant.now();
            Map<String, Object> map = objectMapper.readValue(wikipediaJSON, new TypeReference<Map<String, Object>>() {});
            Map<String, Object> query = (Map<String, Object>) map.get("query");
            Map<String, Object> pages = (Map<String, Object>) query.get("pages");
            Optional<Object> first = pages.values().stream().findFirst();
            String extract = (String) ((LinkedHashMap) first.get()).get("extract");
            wikipediaResponseDTO.setExtract(extract);
            //TODO set to debug
            LOG.info("Request to {} took {} ", new Object[]{wikipediaExtractEndpoint + title, Duration.between(before, after)});
        } catch (HttpStatusCodeException ex) {
            LOG.error(ErrorMessages.HTTP_RESPONSE_ERROR, ex);
            wikipediaResponseDTO.setError(ErrorCodes.WIKIPEDIA_GENERIC, ErrorMessages.HTTP_RESPONSE_ERROR);
        } catch (IOException e) {
            LOG.error(ErrorMessages.JSON_PARSING_ERROR, e);
            wikipediaResponseDTO.setError(ErrorCodes.WIKIPEDIA_GENERIC, ErrorMessages.JSON_PARSING_ERROR);
        }
        return wikipediaResponseDTO;
    }

}