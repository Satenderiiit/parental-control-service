package com.bskyb.internettv.common;

import java.util.LinkedHashMap;
import java.util.Map;

public class ParentalLevelCode {

    public static final Map<String, Integer> PARENTAL_CODE_LEVEL = new LinkedHashMap<>();

    static {
        PARENTAL_CODE_LEVEL.put("U", 0);
        PARENTAL_CODE_LEVEL.put("PG", 1);
        PARENTAL_CODE_LEVEL.put("12", 2);
        PARENTAL_CODE_LEVEL.put("15", 3);
        PARENTAL_CODE_LEVEL.put("18", 4);
    }
}
