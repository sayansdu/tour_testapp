package com.sayan.testapp;

import spark.utils.IOUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static java.net.HttpURLConnection.*;
import static org.junit.Assert.fail;

public class RestClientUtils {

    static List<Integer> SUCCESS_STATUSES = Arrays.asList(HTTP_OK, HTTP_CREATED);

    public static class Response {
        private String body;
        private int status;

        Response(int status, String body) {
            this.status = status;
            this.body = body;
        }

        public String getBody() {
            return body;
        }

        public int getStatus() {
            return status;
        }
    }

    static Response request(String method, String path, String input) {
        HttpURLConnection connection = null;
        Response response;

        try {
            URL url = new URL("http://localhost:" + Main.PORT + path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            if (input != null) {
                try( DataOutputStream dos = new DataOutputStream(connection.getOutputStream())) {
                    dos.write(input.getBytes());
                }
            } else {
                connection.connect();
            }

            if (SUCCESS_STATUSES.contains(connection.getResponseCode())) {
                String body = IOUtils.toString(connection.getInputStream());
                response = new Response(connection.getResponseCode(), body);
            } else {
                response = new Response(connection.getResponseCode(), null);
            }

        } catch (IOException e) {
            fail("Request failed: " + e.getMessage());
            response = new Response(HTTP_INTERNAL_ERROR, e.getLocalizedMessage());
        } finally {
            if (connection != null)
                connection.disconnect();
        }

        return response;
    }

    static Response delete(String path) {
        HttpURLConnection connection = null;
        Response response;

        try {
            URL url = new URL("http://localhost:" + Main.PORT + path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-Type", "application/json");

            if (SUCCESS_STATUSES.contains(connection.getResponseCode())) {
                String body = IOUtils.toString(connection.getInputStream());
                response = new Response(connection.getResponseCode(), body);
            } else {
                response = new Response(connection.getResponseCode(), null);
            }

        } catch (IOException e) {
            fail("Request failed: " + e.getMessage());
            response = new Response(HTTP_INTERNAL_ERROR, e.getLocalizedMessage());
        } finally {
            if (connection != null)
                connection.disconnect();
        }

        return response;
    }
}
