package com.bskyb.internettv.third.party;

public interface MovieService {
        String getParentalControlLevel(String titleId) throws TitleNotFoundException, TechnicalFailureException;
}
