package edu.unca.csci338.domain.data;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import edu.unca.csci338.domain.model.Room;

public class RoomData {
    public static final String CSCI_DATABASE = "edu_unca_csci338";

    private static final String ROW_ID = "ID";
    private static final String ROW_ROOM_NUM = "room_number";
    private static final String ROW_CAPACITY = "capacity";
    private static final String ROW_ROOM_TYPE = "room_type";

    private Connection connection = null;

    /** Hardcoded toggle to output status of database interactions */
    private static final boolean VERBOSE_MODE = true;

    /**
     * TODO: Database credentials are hardcoded and should be changed and
     * standardized between dev environments.
     * 
     * @param databaseName
     * @return whether or not the connection was successful.
     */
    public boolean Connect(String databaseName) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/" + databaseName, "root", "password123");
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s\n", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (VERBOSE_MODE) {
            if (connection != null) {
                System.out.println("Connected to the database");
            } else {
                System.out.println("Failed to make connection");
            }
        }

        return connection != null;
    }

    /**
     * Close connection to database, requires call to Connect() for future
     * modification.
     * 
     * @return whether or not the disconnection was successful.
     */
    public boolean Disconnect() {
        try {
            connection.close();
            connection = null;
            if (VERBOSE_MODE) {
                System.out.println("Closed connection to database");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s\n", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection == null;
    }

    /**
     * Generic helper function to handle SELECT statements
     * 
     * @param statement for mySQL database query.
     * @return {@code ArrayList<Room>} of selected rooms, if any.
     */
    private ArrayList<Room> getRoomsWithStatement(String statement) {
        ResultSet resultSet = null;
        ArrayList<Room> output = new ArrayList<Room>();

        if (VERBOSE_MODE) {
            System.out.println("mysql>" + statement);
        }

        try {
            resultSet = connection.prepareStatement(statement).executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(ROW_ID);
                int roomNum = resultSet.getInt(ROW_ROOM_NUM);
                int capacity = resultSet.getInt(ROW_CAPACITY);
                //RoomType roomType = RoomType.fromString(resultSet.getString(ROW_ROOM_TYPE));
                Room room = new Room(roomNum, capacity); //, roomType);
                room.setId(id);

                output.add(room);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s\n", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (VERBOSE_MODE) {
            System.out.println(output.size() + " Queried Rooms [");
            for (Room item : output) {
                System.out.println(" " + item.toString());
            }
            System.out.println("]");
        }

        return output;
    }

    /** @return all rooms stored in the database. */
    public ArrayList<Room> getAllRooms() {
        return getRoomsWithStatement("SELECT * FROM rooms");
    }

    /**
     * TODO: Possibly change buildingID when mySQL relationship is better understood
     * 
     * @param buildingID mySQL foreign key.
     * @return all rooms stored in the database with the buildingID.
     */
    public ArrayList<Room> getRoomsFromBuildingID(int buildingID) {
        return getRoomsWithStatement("SELECT * FROM rooms "
                + "WHERE building_id='" + String.valueOf(buildingID) + "'"
                + ";");
    }

    /**
     * TODO: Possibly change buildingID when mySQL relationship is better understood
     * 
     * @param buildingID mySQL foreign identifier.
     * @param roomNumber real-world identifier of a room.
     * @return Room with matching criteria or null if room is not found.
     */
    public Room getRoomByBuildingAndNumber(int buildingID, int roomNumber) {
        ArrayList<Room> results = getRoomsWithStatement("SELECT * FROM rooms "
                + "WHERE building_id='" + roomNumber + "'"
                + "AND room_number='" + roomNumber + "'"
                + ";");

        if (results.isEmpty()) {
            return null;
        }

        return results.get(0);
    }

    /**
     * @param id mySQL identifier.
     * @return Room with matching roomID or null if room is not found.
     */
    public Room getRoomByID(int id) {
        ArrayList<Room> results = getRoomsWithStatement("SELECT * FROM rooms WHERE id=" + id);
        if (results.isEmpty()) {
            return null;
        }

        return results.get(0);
    }

    /**
     * TODO: Change buildingID when mySQL relationship is better understood
     * 
     * @param buildingID mySQL foreign key to be checked.
     * @param roomNumber value to be checked in the database.
     * @return whether or not the potential room_number is available.
     */
    public boolean isRoomNumberForBuildingAvailable(int buildingID, int roomNumber) {
        return getRoomsWithStatement("SELECT * FROM rooms "
                + "WHERE building_id='" + buildingID + "'"
                + "AND room_number='" + roomNumber + "'"
                + ";").isEmpty();
    }

    /**
     * @param room target to add to the database.
     * @return mySQL identifier of the newly inserted table's row.
     * 
     * @throws DuplicateRoomNumberInBuildingException if room number is already
     *                                                being used.
     */
    public int addRoom(Room room)  {
		/*
		 * if (isRoomNumberForBuildingAvailable(1, room.getRoomNum()) == false) { throw
		 * new DuplicateRoomNumberInBuildingException(); }
		 */

        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        int generatedID = -1;

        String[] data = new String[] {
                String.valueOf(1), // mySQL foreign key: building_id
                String.valueOf(room.getRoomNum()),
                String.valueOf(room.getCapacity()),
                //room.getRoomType().toString(),
        };
        String statement = "INSERT INTO rooms(building_id, room_number, capacity, room_type)"
                + " VALUES ('" + String.join("','", data) + "');";

        if (VERBOSE_MODE) {
            System.out.println("mysql>" + statement);
        }

        try {
            preparedStatement = connection.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.execute();

            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                generatedID = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n", e.getSQLState());
            System.err.format("Error inserting data into the 'rooms' table: " + e.getMessage() + "\n");
        }

        room.setId(generatedID);
        if (VERBOSE_MODE) {
            System.out.println("Successfully added room at row-id: " + generatedID);
        }

        return generatedID;
    }

    /**
     * @param room target to add to the database.
     * @return whether or not the operation succeeded.
     */
    public boolean updateRoom(Room room) {
        PreparedStatement preparedStatement = null;
        String statement = "UPDATE rooms SET "
                + " building_id = '" + 1 + "'"
                + ", room_number = '" + room.getRoomNum() + "'"
                + ", capacity = '" + room.getCapacity() + "'"
                //+ ", room_type = '" + room.getRoomType().toString() + "'"
                + "WHERE id = '" + room.getId() + "'"
                + ";";

        if (VERBOSE_MODE) {
            System.out.println("mysql>" + statement);
        }

        try {
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.execute();

            int changedRowCount = preparedStatement.executeUpdate();
            if (VERBOSE_MODE) {
                System.out.println(changedRowCount + " rows were updated.");
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n", e.getSQLState());
            System.err.format("Error updating data into the 'rooms' table: " + e.getMessage() + "\n");
            return false;
        }

        return true;
    }

    /**
     * TODO: Fails due to foreign key constraint with courseInstances
     * 
     * @param room the room to be deleted.
     * @return whether or not the operation succeeded in deleting at max a single
     *         room. Will return true if no matching rooms were found in the
     *         database.
     */
    public boolean deleteRoom(Room room) {
        PreparedStatement statement = null;
        String command = "DELETE FROM rooms WHERE id='" + room.getId() + "'";

        if (VERBOSE_MODE) {
            System.out.println("mysql>" + statement);
        }

        try {
            statement = connection.prepareStatement(command);
            int changedRowCount = statement.executeUpdate();

            if (VERBOSE_MODE) {
                if (changedRowCount == 1) {
                    System.out.println("Successfully deleted room with id: " + room.getId());
                } else if (changedRowCount == 0) {
                    System.out.println("FAILED to find room to delete with id: " + room.getId());
                }
            }

            if (changedRowCount > 1) {
                System.err.println("POSSIBLY FAILED to delete room with id: " + room.getId());
                System.err.println(changedRowCount + " rooms deleted");
            }

            return changedRowCount <= 1;

        } catch (SQLException e) {
            System.err.println("Error deleting data from the 'rooms' table: " + e.getMessage());
            return false;
        }
    }
}
