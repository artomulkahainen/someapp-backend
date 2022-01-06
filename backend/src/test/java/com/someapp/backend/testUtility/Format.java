package com.someapp.backend.testUtility;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Format {
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
