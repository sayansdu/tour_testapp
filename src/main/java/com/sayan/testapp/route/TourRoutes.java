package com.sayan.testapp.route;

import com.sayan.testapp.model.Tour;
import com.sayan.testapp.service.TourService;

import java.util.List;

import static com.sayan.testapp.json.JsonUtil.fromJson;
import static com.sayan.testapp.json.JsonUtil.toJson;
import static spark.Spark.*;

public class TourRoutes {

    private static String JSON_CONTENT_TYPE = "application/json";
    private static String TOUR_NOT_FOUND = "tour not found";

    public static void allRoutes(TourService tourService) {
        get("/", (req, res) -> {
            return toJson("Welcome to Tour service");
        });

        get("/tour", (req, res) -> {
            List<Tour> tours = tourService.findAll();
            if (!tours.isEmpty()) {
                return toJson(tours);
            } else {
                res.status(404);
                return toJson(TOUR_NOT_FOUND);
            }
        });

        get("/tour/:id", (req, res) -> {
            Tour tour = tourService.findById(Long.parseLong(req.params("id")));
            if (tour != null) {
                return toJson(tour);
            } else {
                res.status(404);
                return toJson(TOUR_NOT_FOUND);
            }
        });

        post("/tour", (req, res) -> {
            Tour inputTour = fromJson(req.body());
            Tour tour = tourService.create(inputTour);
            res.status(201); // 201 Created
            return toJson(tour);
        });

        put("/tour/:id", (req, res) -> {
            long id = Long.parseLong(req.params(":id"));
            Tour tour = tourService.findById(id);
            if (tour != null) {
                Tour inputTour = fromJson(req.body());
                tourService.update(inputTour);
                return toJson("tour with id " + id + " is updated!");
            } else {
                res.status(404);
                return toJson(TOUR_NOT_FOUND);
            }
        });

        delete("/tour/:id", (req, res) -> {
            long id = Long.parseLong(req.params(":id"));
            Tour tour = tourService.findById(id);
            if (tour != null) {
                tourService.delete(id);
                return toJson("tour with id " + id + " is deleted!");
            } else {
                res.status(404);
                return toJson(TOUR_NOT_FOUND);
            }
        });

        after((req, res) -> res.type(JSON_CONTENT_TYPE));
    }
}
