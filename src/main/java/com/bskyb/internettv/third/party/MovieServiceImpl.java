package com.bskyb.internettv.third.party;

import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {
    @Override
    public String getParentalControlLevel(String titleId) throws TitleNotFoundException, TechnicalFailureException {
        return "";
    }
}
