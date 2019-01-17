package com.bskyb.internettv.service;

public interface ParentalControlService {
    boolean canWatchMovie(String customerParentalControlLevel, String movieId) throws Exception;
}
