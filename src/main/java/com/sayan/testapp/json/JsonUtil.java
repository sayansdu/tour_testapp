package com.sayan.testapp.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sayan.testapp.model.Tour;

import java.io.IOException;

public class JsonUtil {

    private static ObjectMapper om = new ObjectMapper();

    public static String toJson(Object object) throws JsonProcessingException {
        return om.writeValueAsString(object);
    }

    public static Tour fromJson(String value) throws IOException {
        return om.readValue(value, Tour.class);
    }
}
