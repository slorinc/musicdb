package com.cabonline.musicdb.service;

import com.cabonline.musicdb.dto.*;
import com.cabonline.musicdb.dto.builder.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.scheduling.annotation.AsyncResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by s_lor_000 on 11/29/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class MusicFacadeTest {

    @Mock
    private MusicBrainzIntegration musicBrainzIntegration;

    @Mock
    private WikipediaIntegration wikipediaIntegration;

    @Mock
    private CoverArtArchiveIntegration coverArtArchiveIntegration;

    @InjectMocks
    private MusicFacadeImpl musicFacade;

    @Test
    public void testQuery() {
        MusicBrainzResponseDTO musicBrainzResponseDTO = new MusicBrainzResponseDTO();
        ReleaseDTO releaseDTO = new ReleaseDTOBuilder().setMdId(APIIntegrationTest.COVERART_MBID).setPrimaryType("Album").setTitle("Steak").createReleaseDTO();
        RelationDTO relationDTO = new RelationDTOBuilder().setType("wikipedia").setUrl(new UrlDTOBuilder().setResource("http://en.wikipedia.org/wiki/Mr._Oizo").createUrlDTO()).createRelationDTO();
        musicBrainzResponseDTO.setRelationList(new ArrayList<>(Arrays.asList(relationDTO)));
        musicBrainzResponseDTO.setReleasesList(new ArrayList<>(Arrays.asList(releaseDTO)));
        when(musicBrainzIntegration.query(APIIntegrationTest.MBID)).thenReturn(musicBrainzResponseDTO);
        Map<String, Map<String, String>> pages = new LinkedHashMap<>();
        Map<String, String> page = new LinkedHashMap<>();
        page.put("extract","This is the extract");
        pages.put("123123",page);
        WikipediaResponseDTO wikipediaResponseDTO = new WikipediaResponseDTOBuilder().setQuery(new QueryDTOBuilder().setPages(pages).createQueryDTO()).createWikipediaResponseDTO();
        when(wikipediaIntegration.query(APIIntegrationTest.WIKIPAGE_TITLE)).thenReturn(wikipediaResponseDTO);
        final CoverArtArchiveResponseDTO coverArtArchiveResponseDTO = new CoverArtArchiveResponseDTOBuilder()
                .setImages(new ArrayList<>(Arrays.asList(new CoverArtImagesDTOBuilder().setImage("http://test.com/image.jpg").createCoverArtImagesDTO())))
                .createCoverArtArchiveResponseDTO();

        when(coverArtArchiveIntegration.query(APIIntegrationTest.COVERART_MBID)).thenReturn(new AsyncResult<CoverArtArchiveResponseDTO>(coverArtArchiveResponseDTO));
        final MusicDBResponseDTO musicDBResponseDTO = musicFacade.query(APIIntegrationTest.MBID);

        assertThat("mbid",musicDBResponseDTO.getMbId(),equalTo(APIIntegrationTest.MBID));
        assertThat("description",musicDBResponseDTO.getDescription(), equalTo("This is the extract"));
        assertThat("albums size", musicDBResponseDTO.getAlbums().size(), equalTo(1));
        assertThat("album title", musicDBResponseDTO.getAlbums().get(0).getTitle(), equalTo("Steak"));
        assertThat("coverart", musicDBResponseDTO.getAlbums().get(0).getImage(),equalTo("http://test.com/image.jpg"));
        
    }
}
