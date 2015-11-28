package com.cabonline.musicdb.service;

import com.cabonline.musicdb.dto.WikipediaResponseDTO;

/**
 * Created by s_lor_000 on 11/24/2015.
 */
public interface WikipediaIntegration {

    WikipediaResponseDTO query(String title);
}
