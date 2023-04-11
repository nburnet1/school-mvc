package edu.unca.csci338.domain.model;

import java.util.HashMap;

public class Building {

    private int id;
    private int numRooms;
    private String name;

    public Building(int id, String name, int numRooms) {
        this.id = id;
        this.name = name;
        this.numRooms = numRooms;
    }

    public int getId() {
        return id;
    }

    public int getNumRooms() {
        return numRooms;
    }

    public void setNumRooms(int numRooms) {
        this.numRooms = numRooms;
    }

    public void addRoom(HashMap room) {
    }

    public void removeRoom() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

