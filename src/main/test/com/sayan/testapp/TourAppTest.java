package com.sayan.testapp;

import com.sayan.testapp.model.Tour;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import spark.Spark;

import static com.sayan.testapp.data.TestData.TEST_JSON;
import static com.sayan.testapp.json.JsonUtil.fromJson;
import static com.sayan.testapp.json.JsonUtil.toJson;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_OK;
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
    public void checkTourApp_successCase() {
        RestClientUtils.Response response = RestClientUtils.request("POST", "/tour", TEST_JSON);

        assertEquals(HTTP_OK, response.getStatus());

        Tour tour = fromJson(response.getBody());
        assertEquals("TestCity", tour.getName());
        assertEquals("test tour", tour.getDescription());
        assertTrue(tour.getId() > 0);

        tour.setDescription("test tour2");
        response = RestClientUtils.request("PUT", "/tour/" + tour.getId(), toJson(tour));

        assertEquals(HTTP_OK, response.getStatus());

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

        assertEquals(HTTP_INTERNAL_ERROR, response.getStatus());
    }

    @Test
    public void findTourById_idNotNumber() {
        RestClientUtils.Response response = RestClientUtils.request("GET", "/tour/test", null);

        assertEquals(HTTP_INTERNAL_ERROR, response.getStatus());
    }
}
