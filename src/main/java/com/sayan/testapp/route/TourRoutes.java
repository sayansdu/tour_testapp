package com.sayan.testapp.route;

import com.sayan.testapp.service.TourService;

import static com.sayan.testapp.json.JsonUtil.*;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static spark.Spark.*;

public class TourRoutes {

    private static String JSON_CONTENT_TYPE = "application/json";

    public static void allRoutes(TourService tourService) {
        get("/", (req, res) -> "Welcome to Tour service", json());

        get("/tour", (req, res) -> tourService.findAll(), json());

        get("/tour/:id", (req, res) -> tourService.findById(req.params("id")), json());

        post("/tour", (req, res) -> tourService.create(fromJson(req.body())), json());

        put("/tour/:id", (req, res) -> tourService.update(req.params(":id"), fromJson(req.body())), json());

        delete("/tour/:id", (req, res) -> tourService.delete(req.params(":id")), json());

        after((req, res) -> res.type(JSON_CONTENT_TYPE));

        exception(RuntimeException.class, (e, req, res) -> {
            res.status(HTTP_INTERNAL_ERROR);
            res.body(toJson(new ErrorResponse(e)));
        });
    }
}
