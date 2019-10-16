package com.sayan.testapp.repository;

import com.sayan.testapp.model.Tour;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TourRepository {

    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/testapp";

    private static final String USER = "admin";
    private static final String PASS = "password";

    private static final String SELECT_ALL_QUERY = "SELECT * FROM TOUR";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM TOUR WHERE id = ?";
    private static final String INSERT_QUERY = "INSERT INTO TOUR(name, description) VALUES (?, ?)";
    private static final String UPDATE_QUERY = "UPDATE TOUR SET name = ?, description = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM TOUR WHERE id = ?";

    private Connection conn = null;

    public TourRepository() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement stmt = conn.createStatement();
            String sql =  "CREATE TABLE IF NOT EXISTS TOUR " +
                    "(id bigint auto_increment PRIMARY KEY, " +
                    " name VARCHAR(50), " +
                    " description VARCHAR(255))";

            stmt.executeUpdate(sql);
            stmt.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public Tour findById(long inputId) throws SQLException {
        Tour tour = null;

        PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_QUERY);
        stmt.setLong(1, inputId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            long id  = rs.getInt("id");
            String name = rs.getString("name");
            String description = rs.getString("description");
            tour = new Tour(id, name, description);
        }
        stmt.close();
        rs.close();

        return tour;
    }

    public List<Tour> findAll() throws SQLException {
        List<Tour> tours = new ArrayList<>();

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(SELECT_ALL_QUERY);

        while (rs.next()) {
            long id  = rs.getInt("id");
            String name = rs.getString("name");
            String description = rs.getString("description");
            tours.add(new Tour(id, name, description));
        }
        stmt.close();
        rs.close();

        return tours;
    }

    public Tour create(Tour tour) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, tour.getName());
        statement.setString(2, tour.getDescription());

        int affectedRows = statement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating tour failed, no rows affected.");
        }

        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) {
            tour.setId(rs.getLong(1));
        }
        else {
            throw new SQLException("Creating user failed, no ID obtained.");
        }

        statement.close();
        rs.close();

        return tour;
    }

    public Tour update(Tour  tour) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(UPDATE_QUERY);
        statement.setString(1, tour.getName());
        statement.setString(2, tour.getDescription());
        statement.setLong(3, tour.getId());

        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Updating tour failed, no rows affected.");
        }

        statement.close();
        return tour;
    }

    public void delete(long id) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(DELETE_QUERY);
        statement.setLong(1, id);

        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Deleting tour failed, no rows affected.");
        }

        statement.close();
    }

}
