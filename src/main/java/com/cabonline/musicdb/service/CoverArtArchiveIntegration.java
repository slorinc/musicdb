package com.cabonline.musicdb.service;

import com.cabonline.musicdb.dto.CoverArtArchiveResponseDTO;

import java.util.concurrent.Future;

/**
 * Created by s_lor_000 on 11/24/2015.
 */
public interface CoverArtArchiveIntegration {
    Future<CoverArtArchiveResponseDTO> query(String mbId);
}
