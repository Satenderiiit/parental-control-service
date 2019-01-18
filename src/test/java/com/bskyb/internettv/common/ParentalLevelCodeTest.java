package com.bskyb.internettv.common;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertThat;

public class ParentalLevelCodeTest {

    Map<String, Integer> actualParentalCodeMap = ParentalLevelCode.PARENTAL_CODE_LEVEL;

    @Test
    public void shouldReturnParentalCodeLevelsBasedForRequestedParentalCode() {
        assertThat("Mismatch parental code level order for U", actualParentalCodeMap.get("U"), Matchers.equalTo(0));
        assertThat("Mismatch parental code level order for PG", actualParentalCodeMap.get("PG"), Matchers.equalTo(1));
        assertThat("Mismatch parental code level order for 12", actualParentalCodeMap.get("12"), Matchers.equalTo(2));
        assertThat("Mismatch parental code level order for 15", actualParentalCodeMap.get("15"), Matchers.equalTo(3));
        assertThat("Mismatch parental code level order for 18", actualParentalCodeMap.get("18"), Matchers.equalTo(4));

    }
}