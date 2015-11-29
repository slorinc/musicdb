package com.cabonline.musicdb;

/**
 * Created by s_lor_000 on 11/29/2015.
 */

import com.cabonline.musicdb.dto.CoverArtArchiveResponseDTO;
import com.cabonline.musicdb.dto.MusicBrainzResponseDTO;
import com.cabonline.musicdb.service.CoverArtArchiveIntegration;
import com.cabonline.musicdb.service.MusicBrainzIntegration;
import com.jayway.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.Assert.*;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class ApplicationTest {

    private static final String MBID = "a4a3048f-3968-4848-9f53-94e3d4f88b53";
    private static final String WIKIPAGE_TITLE = "Mr._Oizo";
    private static final String CONTEXT_PATH = "/musicdb";
    private static final String COVERART_MBID = "1b022e01-4da6-387b-8658-8678046e4cef";

    @Value("${local.server.port}")
    private int serverPort;

    @Value("${wikipedia.extract.endpoint}")
    private String wikipediaExtractEndpoint;

    @Value("${wikipedia.oizo.response.json}")
    private String oizoWikipeadiaResponseJson;

    @Value("${musicbrainz.artist.endpoint}")
    private String musicbrainzArtistEndpoint;

    @Value("${musinbrainz.mroizo.response.json}")
    private String oizoMusicBrainzResponseJson;

    @Value("${coverartarchive.response.json}")
    private String coverartResponseJson;

    @Autowired
    private CoverArtArchiveIntegration coverArtArchiveIntegration;

    @Autowired
    private MusicBrainzIntegration musicBrainzIntegration;

    @Autowired
    private RestTemplate customRestTemplate;

    private MockRestServiceServer mockRestServiceServer;

    @Before
    public void setUp() {
        mockRestServiceServer = MockRestServiceServer.createServer(customRestTemplate);
        RestAssured.port = serverPort;
    }

    @Test
    public void healthEndpoint() {
        when().get(CONTEXT_PATH + "/health")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void testCoverArtRestCall() throws InterruptedException, ExecutionException {
        mockRestServiceServer.expect(requestTo("http://coverartarchive.org/release-group/1b022e01-4da6-387b-8658-8678046e4cef"))
                .andExpect(method(GET))
                .andRespond(withSuccess(coverartResponseJson, APPLICATION_JSON));
        final Future<CoverArtArchiveResponseDTO> query = coverArtArchiveIntegration.query(COVERART_MBID);
        while (!query.isDone()) {
            Thread.sleep(10);
        }
        assertTrue(query.get().getImages().get(0).getImage().equals("http://test.com/image.jpg"));
        System.out.println(query.get());

    }

    @Test
    public void testMusicBrainsRestCall() throws UnsupportedEncodingException {
        String url = musicbrainzArtistEndpoint
                + MBID + "?fmt=json&inc=url-rels%2Brelease-groups";
        mockRestServiceServer.expect(requestTo(url))
                .andExpect(method(GET))
                .andRespond(withSuccess(oizoMusicBrainzResponseJson, APPLICATION_JSON));
        final MusicBrainzResponseDTO musicBrainzResponseDTO = musicBrainzIntegration.query(MBID);
        assertTrue(musicBrainzResponseDTO.getName().equals("Mr. Oizo"));
        assertTrue(musicBrainzResponseDTO.getRelationList().size()==9);
        assertTrue(musicBrainzResponseDTO.getReleasesList().size()==25);
        assertTrue(musicBrainzResponseDTO.getErrorCode()==null);
        assertTrue(musicBrainzResponseDTO.getErrorMessage()==null);


    }

}