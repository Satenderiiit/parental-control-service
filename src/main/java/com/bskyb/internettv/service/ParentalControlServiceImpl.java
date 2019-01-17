package com.bskyb.internettv.service;

import com.bskyb.internettv.third.party.MovieService;
import com.bskyb.internettv.third.party.TechnicalFailureException;
import com.bskyb.internettv.third.party.TitleNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static java.lang.Integer.parseInt;

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
        HashMap<String, Integer> parentalControlLevelMap = getParentalControlLevelListMap();
        log.info("ParentalControlServiceImpl - canWatchMovie START");
        try {
            log.info("ParentalControlServiceImpl - canWatchMovie invoke movieService getParentalControlLevel");
            return parseInt(parentalControlLevelMap.get(movieService.getParentalControlLevel(movieId)).toString()) <=
                    parseInt(parentalControlLevelMap.get(customerParentalControlLevel).toString());

        } catch (TechnicalFailureException tfe) {
            log.info("ParentalControlServiceImpl - canWatchMovie TechnicalFailureException :{}", tfe.getMessage());
            return false;
        } catch (TitleNotFoundException tnfe) {
            throw new TitleNotFoundException("The movie service could not find the given movie");
        } catch (Exception e) {
            throw e;
        }
    }

    private HashMap<String, Integer> getParentalControlLevelListMap() {
        HashMap<String, Integer> parentalControlLevelMap = new HashMap<>();
        parentalControlLevelMap.put("U", 0);
        parentalControlLevelMap.put("PG", 1);
        parentalControlLevelMap.put("12", 2);
        parentalControlLevelMap.put("15", 3);
        parentalControlLevelMap.put("18", 4);
        return parentalControlLevelMap;
    }
}
