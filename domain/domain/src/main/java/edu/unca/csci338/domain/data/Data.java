package edu.unca.csci338.domain.data;

import java.sql.*;

public class Data {
    public Connection conn = null;

    public void Connect(String dbToConnectTo, String username, String pass) {
        // auto close connection
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/" + dbToConnectTo, username, pass); //

            if (conn != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public void Disconnect() {
        try {
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void delete(int ID, String table) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = conn.prepareStatement("DELETE FROM " +table+ " WHERE id = " + ID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getAll(String table) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = conn.prepareStatement("Select * from "+table);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getById(String table, int ID) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = conn.prepareStatement("Select * from " +table+ " Where id = " + String.valueOf(ID));
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getRecent(String table) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = conn.prepareStatement("Select * from "+table+" Order by id DESC Limit 1");
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return resultSet;

    }




}
