package com.sayan.testapp;

import com.sayan.testapp.route.TourRoutes;
import com.sayan.testapp.service.TourService;

import static spark.Spark.port;

public class Main {

    private static TourService tourService = new TourService();
    static int PORT = 8080;

    public static void main(String[] args) {
        port(PORT);

        TourRoutes.allRoutes(tourService);
    }

}