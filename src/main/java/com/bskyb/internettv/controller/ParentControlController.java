package com.bskyb.internettv.controller;

import com.bskyb.internettv.service.ParentalControlService;
import com.bskyb.internettv.swagger.api.PclApi;
import com.bskyb.internettv.third.party.TitleNotFoundException;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ParentControlController implements PclApi {


    private static final String MOVIE_WATCH_ELIGIBILITY = "/pcl/movie/v1/watch/eligibility";
    private static final String ALPHABET_REGEX = "[a-zA-Z]+";
    private static final String NUMBER_REGEX = "[0-9]+";
    private static final String NO_SPECIAL_CHAR_REGEX = "[a-zA-Z0-9 ]*";

    @Autowired
    private ParentalControlService parentalControlService;

    @GetMapping(value = MOVIE_WATCH_ELIGIBILITY + "/{control_level}" + "/{movie_id}")
    public ResponseEntity<Boolean> getControlAccess(@ApiParam(value = "set control level", required = true)
                                                    @PathVariable("control_level") String controlLevel,
                                                    @ApiParam(value = "movie id of desired movie",
                                                            required = true) @PathVariable("movie_id")
                                                            String movieId) {

        log.info("{} : {}-ParentControlController - getControlAccess START", controlLevel, movieId);
        if (!isValidControlLevel(controlLevel) || containsSpecialCharacters(movieId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            boolean watchEligibility = parentalControlService.canWatchMovie(controlLevel, movieId);
            log.info("{} : {}-ParentControlController - getControlAccess END", controlLevel, movieId);
            return new ResponseEntity<>(new Boolean(watchEligibility), HttpStatus.OK);
        } catch (TitleNotFoundException te) {
            log.info("{} : {}-ParentControlController - getControlAccess TitleNotFoundException : {}", controlLevel, movieId, te.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.info("{} : {}-ParentControlController - getControlAccess Exception : {}", controlLevel, movieId, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean isValidControlLevel(String controlLevel) {
        return controlLevel.matches(ALPHABET_REGEX) || controlLevel.matches(NUMBER_REGEX);
    }

    private boolean containsSpecialCharacters(String movieId) {
        return !movieId.matches(NO_SPECIAL_CHAR_REGEX);
    }
}
