package com.sayan.testapp;

import com.sayan.testapp.model.Tour;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import spark.Spark;

import java.io.IOException;

import static com.sayan.testapp.data.TestData.TEST_JSON;
import static com.sayan.testapp.json.JsonUtil.fromJson;
import static com.sayan.testapp.json.JsonUtil.toJson;
import static java.net.HttpURLConnection.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TourAppTest {

    @BeforeClass
    public static void before() {
        Main.main(null);
    }

    @AfterClass
    public static void after() {
        Spark.stop();
    }

    @Test
    public void checkAllTours() {
        RestClientUtils.Response response = RestClientUtils.request("GET", "/tour", null);

        assertEquals(HTTP_OK, response.getStatus());
    }

    @Test
    public void checkTourApp_successCase() throws IOException {
        RestClientUtils.Response response = RestClientUtils.request("POST", "/tour", TEST_JSON);

        assertEquals(HTTP_CREATED, response.getStatus());

        Tour tour = fromJson(response.getBody());
        assertEquals("TestCity", tour.getName());
        assertEquals("test tour", tour.getDescription());
        assertTrue(tour.getId() > 0);

        tour.setDescription("test tour2");
        response = RestClientUtils.request("PUT", "/tour/" + tour.getId(), toJson(tour));

        assertEquals(HTTP_OK, response.getStatus());
//        assertEquals("tour with id " + tour.getId() + " is updated!", response.getBody());

        String name = tour.getName();
        response = RestClientUtils.request("GET", "/tour/" + tour.getId(), null);

        assertEquals(HTTP_OK, response.getStatus());

        tour = fromJson(response.getBody());
        assertEquals(name, tour.getName());

        response = RestClientUtils.delete("/tour/" + tour.getId());

        assertEquals(HTTP_OK, response.getStatus());
    }

    @Test
    public void findTourById_notFound() {
        RestClientUtils.Response response = RestClientUtils.request("GET", "/tour/0", null);

        assertEquals(HTTP_NOT_FOUND, response.getStatus());
    }
}
