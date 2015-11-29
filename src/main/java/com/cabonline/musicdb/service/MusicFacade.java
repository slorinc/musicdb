package com.cabonline.musicdb.service;

import com.cabonline.musicdb.dto.MusicDBResponseDTO;

import java.io.IOException;

/**
 * Created by s_lor_000 on 11/24/2015.
 */
public interface MusicFacade {

    MusicDBResponseDTO query(String mbId);
}
