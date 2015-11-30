package com.cabonline.musicdb.controller;

import com.cabonline.musicdb.dto.ErrorDTO;
import com.cabonline.musicdb.dto.MusicDBResponseDTO;
import com.cabonline.musicdb.dto.builder.ErrorDTOBuilder;
import com.cabonline.musicdb.error.ErrorCodes;
import com.cabonline.musicdb.error.ErrorMessages;
import com.cabonline.musicdb.service.MusicFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by s_lor_000 on 11/24/2015.
 */
@Controller
@RequestMapping("/public/mbid")
public class MusicController {

    private static final Logger LOG = LoggerFactory.getLogger(MusicController.class);

    @Autowired
    private MusicFacade musicFacade;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value = "/{mbid}")
    public ResponseEntity<? extends ErrorDTO> retrieveMusicData(@PathVariable("mbid") String mbId) throws IOException {
        Pattern pattern = Pattern.compile("[a-f0-9]{8}\\-[a-f0-9]{4}\\-[a-f0-9]{4}\\-[a-f0-9]{4}\\-[a-f0-9]{12}");
        Matcher matcher = pattern.matcher(mbId);
        if (mbId.length()!=36||!matcher.matches()){
            ErrorDTO errorDTO = new ErrorDTOBuilder().setErrorCode(ErrorCodes.MUSICDB_GENERIC).setErrorMessage(ErrorMessages.MBID_VALIDATION_ERROR).createErrorDTO();
            return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(musicFacade.query(mbId), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleError(HttpServletRequest req, Exception exception) {
        LOG.error("Request: " + req.getRequestURL() + " raised " + exception);
        ErrorDTO errorDTO = new ErrorDTOBuilder().setErrorCode(ErrorCodes.MUSICDB_GENERIC).setErrorMessage(ErrorMessages.UNEXPECTED_ERROR).createErrorDTO();
        return new ResponseEntity<>(errorDTO, HttpStatus.SERVICE_UNAVAILABLE);
    }

}
