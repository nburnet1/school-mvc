package edu.unca.csci338.domain.model;

import java.util.ArrayList;
import java.util.Date;

public class CourseInstance {

    int id;
    int startTime;
    int endTime;
    //	ArrayList<Student> students = new ArrayList<Student>();
    int currentCourse;
    int prof;
    int roomId;


    public int getCurrentCourse() {
        return currentCourse;
    }


    public void setCurrentCourse(int currentCourse) {
        this.currentCourse = currentCourse;
    }


    public int getProf() {
        return prof;
    }


    public void setProf(int prof) {
        this.prof = prof;
    }

    public int getID()
    {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public CourseInstance() {

    }

    public CourseInstance(int id, int currentCourse, int prof, int roomId, int startTime, int endTime) {
        this.id=id;
        this.currentCourse = currentCourse;
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomId=roomId;
        this.prof=prof;

    }



    public int getStartTime() {
        return this.startTime;
    }


    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return this.endTime;
    }


    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }




    public int getRoomId() {
        return roomId;
    }


    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

//	public ArrayList<Student> getStudents() {
//		return students;
//	}
//
//
//	public void setStudents(ArrayList<Student> students) {
//		this.students = students;
//	}

}