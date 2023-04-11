package edu.unca.csci338.domain.model;

public class Room {
    private static final int NOT_SAVED_TO_DATABASE_ID = -1;

    private int id;
    private int roomNum;
    private int capacity;
    //private RoomType roomType;
    // private Schedule schedule;

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

    public boolean isSavedToDatabase() {
        return id != NOT_SAVED_TO_DATABASE_ID;
    }

    /*
     * public RoomType getRoomType() { return roomType; }
     */

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    /*
     * public void setRoomType(RoomType roomType) { this.roomType = roomType; }
     */

    public Room(int roomNum, int capacity) { //, RoomType roomType) {
        this.id = NOT_SAVED_TO_DATABASE_ID;
        this.roomNum = roomNum;
        this.capacity = capacity;
        //this.roomType = roomType;

        /*
         * TODO: Add logic to check for duplicate roomNum for a building
         * - Will need a way to reference a building to query it (not a stroke
         * reference)
         * - Might be good to make and throw a RoomWithDuplicateNumberException
         */
    }

    @Override
    public String toString() {
        return String.format("Room(id=%d, roomNum=%d, capacity=%d, roomType=%s)", id, roomNum, capacity);//, roomType);
    }

    /*
     * public static void main(String[] args) { RoomData roomData = new RoomData();
     * roomData.Connect(RoomData.CSCI_DATABASE);
     *
     * int duplicateRoomNumber = 9999; Room roomA = new Room(duplicateRoomNumber,
     * 20, RoomType.ArtFacility); Room roomB = new Room(duplicateRoomNumber, 25,
     * RoomType.Classroom);
     *
     * try {
     *
     * roomData.addRoom(roomA); roomData.addRoom(roomB); } catch (Exception e) {
     * e.printStackTrace(); } }
     */
}
