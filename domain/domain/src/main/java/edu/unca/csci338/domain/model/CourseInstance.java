package edu.unca.csci338.domain.model;

import java.util.ArrayList;
import java.util.Date;

public class CourseInstance {

    private int id;
    private int typeId;
    private int professorId;
    private int roomId;


    public CourseInstance(int id, int typeId, int professorId, int roomId) {
        this.id = id;
        this.typeId = typeId;
        this.professorId = professorId;
        this.roomId = roomId;
    }

    public CourseInstance(){

    }

    public int getId() {
        return id;
    }

    public int getProfessorId() {
        return professorId;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}



