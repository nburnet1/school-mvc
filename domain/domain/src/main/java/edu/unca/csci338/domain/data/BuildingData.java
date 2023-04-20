package edu.unca.csci338.domain.data;

import edu.unca.csci338.domain.model.Building;
import edu.unca.csci338.domain.model.IDataChangeEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BuildingData extends Data {

    /**
     * Method for establishing a connection to the database
     *
     * @param dbToConnectTo (String)
     */

    private static List<IDataChangeEvent<Building>> buildingChangedEvents = new ArrayList<IDataChangeEvent<Building>>();


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
        ResultSet resultSet = getById("buildings", ID);
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
        ResultSet resultSet = getAll("buildings");
        Building building = null;
        ArrayList<Building> results = new ArrayList<>();
        //sending out the request to the database for all table contents and receiving an answer
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
        PreparedStatement prep = null;
        ResultSet res = null;
        int buildID;
        try {
            prep = conn.prepareStatement("insert into buildings(num_rooms, name) VALUES( ?,?)");
            prep.setInt(1, building.getNumRooms());
            prep.setString(2, building.getName());
            buildID = prep.executeUpdate();
            building.setId(buildID);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }//end addBuilding()


    /**
     * changing/updating the data in the database for a building so that it matches the data contained
     * <p>
     * in the parameter object
     *
     * @param building (Building)
     */

    public void updateBuilding(Building building) {
        PreparedStatement prep = null;
        try {
            prep = conn.prepareStatement("UPDATE buildings SET name = ?, num_rooms = ? WHERE id = ?");
            prep.setString(1, building.getName());
            prep.setInt(2, building.getNumRooms());
            prep.setInt(3, building.getId());
            prep.executeUpdate();
            for (IDataChangeEvent<Building> listener : buildingChangedEvents) {
                listener.onDataChanged(building);
            }


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}

