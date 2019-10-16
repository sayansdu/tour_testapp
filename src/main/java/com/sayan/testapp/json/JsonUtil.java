package com.sayan.testapp.json;

import com.google.gson.Gson;
import com.sayan.testapp.model.Tour;
import spark.ResponseTransformer;

public class JsonUtil {

    private static Gson GSON = new Gson();

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

    public static ResponseTransformer json() {
        return JsonUtil::toJson;
    }

    public static Tour fromJson(String value) {
        return GSON.fromJson(value, Tour.class);
    }
}
