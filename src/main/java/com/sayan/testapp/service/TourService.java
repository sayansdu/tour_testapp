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

    public Tour findById(long id) throws SQLException {
        return repository.findById(id);
    }

    public Tour create(Tour tour) throws SQLException {
        return repository.create(tour);
    }

    public Tour update(Tour tour) throws SQLException {
        return repository.update(tour);
    }


    public void delete(long id) throws SQLException {
        repository.delete(id);
    }

    public List<Tour> findAll() throws SQLException {
        return repository.findAll();
    }
}
