package edu.unca.csci338.domain.data;

import edu.unca.csci338.domain.model.Building;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class BuildingData {

    /**
     * Method for establishing a connection to the database
     *
     * @param dbToConnectTo (String)
     */

    private Connection conn = null;


    public void Connect(String dbToConnectTo, String username, String pass) {
        // auto close connection
        try {
            //Still need to put the actual connection port in
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/" + dbToConnectTo, username, pass); //
            //print statements to tell whether connection was successful
            if (conn != null) {
                System.out.println("Connected to the database!");

            } else {
                System.out.println("Failed to make connection!");

            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();

        }  //end try/catch

    }// connect()


    /**
     * gets the information stored in the database associated with a certain building id
     * <p>
     * and stores it in a Building object
     *
     * @param ID (int)
     * @return building (Building)
     */

    public Building getBuilding(int ID) {
        //getBuilding() Variables
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Building building = null;
        //sending out the request to the database and receiving an answer
        try {
            preparedStatement = conn.prepareStatement("Select * from buildings Where id=" + String.valueOf(ID));
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException e1) {
            e1.printStackTrace();

        }
        //in case of multiple pings
        try {
            //getting the last ping
            while (resultSet.next()) {
                //setting the variables needed to create a Building object
                int id = resultSet.getInt("id");
                int numRooms = resultSet.getInt("num_Rooms");
                String name = resultSet.getString("name");
                //creating a building object
                building = new Building(id, name, numRooms);

            }//end while

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }//end try/catch
        return building;

    }//end getBuilding()


    /**
     * gets all of the buildings stored in the Database and puts them into an array
     *
     * @return results (ArrayList<Buildings>)
     */

    public ArrayList<Building> getBuildings() {
        //getBuildings() variables
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Building building = null;
        ArrayList<Building> results = new ArrayList<>();
        //sending out the request to the database for all table contents and receiving an answer
        try {
            preparedStatement = conn.prepareStatement("Select * from buildings");
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException e1) {
            e1.printStackTrace();

        }//end try/catch
        try {
            while (resultSet.next()) {//cycles through all of the results
                //sets variables needed to create Building objects
                int id = resultSet.getInt("id");
                int numRooms = resultSet.getInt("num_Rooms");
                String name = resultSet.getString("name");
                //create Building object using info
                building = new Building(id, name, numRooms);
                //add building to array of Buildings
                results.add(building);

            }//end while
            return results;


        } catch (SQLException e) {
            e.printStackTrace();

        }//end try/catch
        return null;

    }//end getBuildings()


    /**
     * stores the information in a Building object into the buildings table in the database
     *
     * @param building (Building)
     */

    public void addBuilding(Building building) {
        //addBuilding() variables
        PreparedStatement preparedStatement = null;
        //getting the data prepared to make the request, sending the request, and executing the request
        try {
            //getting the data prepared to make the request
            int numRooms = building.getNumRooms();
            String name = building.getName();
            //sending the request
            preparedStatement = conn.prepareStatement("insert into buildings ('num_Rooms', 'name') values(" + numRooms + "," + name + ")");
            //executing the request
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }//end try/catch

    }//end addBuilding()


    /**
     * changing/updating the data in the database for a building so that it matches the data contained
     * <p>
     * in the parameter object
     *
     * @param building (Building)
     */

    public void updateBuilding(Building building) {
        //updateBuilding() variables
        PreparedStatement preparedStatement = null;
        int ID = building.getId();
        int numRooms = building.getNumRooms();
        String name = building.getName();
        //sending out a request for the database and executing the request
        try {
            preparedStatement = conn.prepareStatement("update buildings where id = " + ID + "('num_Rooms', 'name') values(" + numRooms + "," + name + ")");
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }//end try/catch

    }//end updateBuilding()


    /**
     * deletes the id and all other data associated with that id from the building database table
     *
     * @param ID (int)
     */

    public void deleteBuilding(int ID) {
        //deleteBuilding() variables
        PreparedStatement preparedStatement = null;
        //sending out a request for the database and executing that request
        try {
            preparedStatement = conn.prepareStatement("delete from buildings where id = " + ID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }//end try/catch

    }//end deleteBuilding()

}

