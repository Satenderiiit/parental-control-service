package com.bskyb.internettv.service;

import com.bskyb.internettv.common.ParentalLevelCode;
import com.bskyb.internettv.third.party.MovieService;
import com.bskyb.internettv.third.party.TechnicalFailureException;
import com.bskyb.internettv.third.party.TitleNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
@Slf4j
public class ParentalControlServiceImpl implements ParentalControlService {

    @Autowired
    private final MovieService movieService;

    public ParentalControlServiceImpl(final MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public boolean canWatchMovie(String customerParentalControlLevel, String movieId) throws Exception {
        Map<String, Integer> parentalControlLevelMap = ParentalLevelCode.PARENTAL_CODE_LEVEL;
        log.info("ParentalControlServiceImpl - canWatchMovie START");
        try {
            log.info("ParentalControlServiceImpl - canWatchMovie invoke movieService getParentalControlLevel");
            Integer movieParentalControlLevel = parentalControlLevelMap.get(movieService.getParentalControlLevel(movieId));
            Integer customerProvidedParentalControlLevel = parentalControlLevelMap.get(customerParentalControlLevel);

            if (containsValidParentalLevelCodes(movieParentalControlLevel, customerProvidedParentalControlLevel)) {
                log.info("ParentalControlServiceImpl - canWatchMovie status");
                return movieParentalControlLevel <= customerProvidedParentalControlLevel;
            } else {
                return false;
            }
        } catch (TechnicalFailureException tfe) {
            log.info("ParentalControlServiceImpl - canWatchMovie TechnicalFailureException :{}", tfe.getMessage());
            return false;
        } catch (TitleNotFoundException tnfe) {
            log.info("ParentalControlServiceImpl - canWatchMovie TitleNotFoundException :{}", tnfe.getMessage());
            throw new TitleNotFoundException("The movie service could not find the given movie");
        } catch (Exception e) {
            log.info("ParentalControlServiceImpl - canWatchMovie general Exception :{}", e.getMessage());
            throw e;
        }
    }

    private boolean containsValidParentalLevelCodes(Integer movieParentalControlLevel, Integer customerProvidedParentalControlLevel) {
        return !StringUtils.isEmpty(movieParentalControlLevel) && !StringUtils.isEmpty(customerProvidedParentalControlLevel);
    }

}
