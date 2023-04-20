package edu.unca.csci338.domain.data;

import edu.unca.csci338.domain.model.IDataChangeEvent;
import edu.unca.csci338.domain.model.Room;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomData extends Data {

    private static List<IDataChangeEvent<Room>> roomChangedEvents = new ArrayList<IDataChangeEvent<Room>>();

    public ArrayList<Room> getRooms() {

        ResultSet resultSet = getAll("rooms");
        Room room = null;
        ArrayList<Room> result = new ArrayList<Room>();


        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int building_id = resultSet.getInt("building_id");
                int room_number = resultSet.getInt("room_number");
                int capacity = resultSet.getInt("capacity");
                String room_type = resultSet.getString("room_type");
                room = new Room(id, building_id, room_number, capacity, room_type);

                result.add(room);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public Room getRoomsByBuildingID(int buildingID) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Room room = null;

        try {
            preparedStatement = conn.prepareStatement("select * from rooms where building_id = " + String.valueOf(buildingID));
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int building_id = resultSet.getInt("building_id");
                int room_number = resultSet.getInt("room_number");
                int capacity = resultSet.getInt("capacity");
                String room_type = resultSet.getString("room_type");
                room = new Room(id, building_id, room_number, capacity, room_type);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return room;
    }

    public Room getRoomsByBuildingIDAndNumber(int buildingID, int roomNumber) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Room room = null;

        try {
            preparedStatement = conn.prepareStatement("select * from rooms where building_id = " + String.valueOf(buildingID)
                    + "AND where room_number = " + String.valueOf(roomNumber));
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int building_id = resultSet.getInt("building_id");
                int room_number = resultSet.getInt("room_number");
                int capacity = resultSet.getInt("capacity");
                String room_type = resultSet.getString("room_type");
                room = new Room(id, building_id, room_number, capacity, room_type);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return room;
    }

    public Room getRoom(int ID) {
        ResultSet resultSet = getById("rooms", ID);
        Room room = null;

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int building_id = resultSet.getInt("building_id");
                int room_number = resultSet.getInt("room_number");
                int capacity = resultSet.getInt("capacity");
                String room_type = resultSet.getString("room_type");
                room = new Room(id, building_id, room_number, capacity, room_type);

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //System.out.println(student.getID());
        return room;
    }

    public boolean isRoomNumberForBuildingAvailable(int buildingID, int roomNumber) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = conn.prepareStatement("SELECT id FROM rooms WHERE building_id = ? AND room_number = ?");
            preparedStatement.setInt(1, buildingID);
            preparedStatement.setInt(2, roomNumber);
            resultSet = preparedStatement.executeQuery();

            return !resultSet.next(); // If there's no next row, the room is available
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public boolean insertRoom(Room room) {
        if (!isRoomNumberForBuildingAvailable(room.getBuildingId(), room.getRoomNum())) {
            return false; // Room number is not available, so return false
        }

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = conn.prepareStatement("INSERT INTO rooms (building_id, room_number, capacity, room_type) VALUES (?, ?, ?, ?)");
            preparedStatement.setInt(1, room.getBuildingId());
            preparedStatement.setInt(2, room.getRoomNum());
            preparedStatement.setInt(3, room.getCapacity());
            preparedStatement.setString(4, room.getRoomType());
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0; // Return true if at least one row was affected by the insert
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public void updateRoom(Room room) {
        PreparedStatement prep = null;

        try {
            prep = conn.prepareStatement("UPDATE rooms SET building_id = ?, room_number = ?, capacity = ?, room_type = ? WHERE id = ?");
            prep.setInt(1, room.getBuildingId());
            prep.setInt(2, room.getRoomNum());
            prep.setInt(3, room.getCapacity());
            prep.setString(4, room.getRoomType());
            prep.setInt(5, room.getId());
            prep.executeUpdate();

            for (IDataChangeEvent<Room> listener : roomChangedEvents) {
                listener.onDataChanged(room);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
