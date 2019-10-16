package com.sayan.testapp.service;

import com.sayan.testapp.model.Tour;
import com.sayan.testapp.repository.TourRepository;

import java.sql.SQLException;
import java.util.List;

public class TourService {

    private TourRepository repository;

    public TourService() {
        repository = new TourRepository();
    }

    public Tour findById(String id) {
        try {
            Tour tour = repository.findById(convertId(id));
            if (tour == null) {
                throw new RuntimeException("tour not found by id: " + id);
            }
            return tour;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }

    }

    public Tour create(Tour tour) {
        try {
            return repository.create(tour);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public Tour update(String id, Tour tour)  {
        try {
            findById(id);
            return repository.update(tour);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public boolean delete(String id) {
        try {
            findById(id);
            repository.delete(convertId(id));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public List<Tour> findAll() {
        try {
            return repository.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    private long convertId(String id) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}
