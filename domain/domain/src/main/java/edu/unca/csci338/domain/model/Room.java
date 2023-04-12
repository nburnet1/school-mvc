package edu.unca.csci338.domain.model;

import edu.unca.csci338.domain.data.RoomData;

public class Room{

    private int id;
    private int roomNum;
    private int capacity;
    private String roomType;
    private int buildingId;

    public Room() {
        //default contructor
    }

    public Room(int id, int buildingId, int roomNum, int capacity, String roomType) {
        this.id = id;
        this.setBuildingId(buildingId);
        this.roomNum = roomNum;
        this.capacity = capacity;
        this.roomType = roomType;

    }

    public int getRoomNum() {
        return roomNum;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void onRoomDataChanged(Room room) {
        // TODO Auto-generated method stub

    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

}