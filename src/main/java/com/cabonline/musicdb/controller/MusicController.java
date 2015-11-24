package com.cabonline.musicdb.controller;

import com.cabonline.musicdb.dto.MusicDBResponseDTO;
import com.cabonline.musicdb.service.MusicFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by s_lor_000 on 11/24/2015.
 */
@RestController
@RequestMapping("/public/mbid")
public class MusicController {


    @Autowired
    private MusicFacade musicFacade;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value = "/{mbid}")
    public MusicDBResponseDTO retrieveMusicData(@PathVariable("mbid") String mbId) throws IOException {
        return musicFacade.query(mbId);
    }

}
