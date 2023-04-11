package edu.unca.csci338.domain.model;

import java.util.ArrayList;
import java.util.Date;

public class CourseInstance {

    int id;
    Date startTime = null;
    Date endTime = null;
    ArrayList<Student> students = new ArrayList<Student>();
    Course currentCourse;
    Professor prof;


    public Course getCurrentCourse() {
        return currentCourse;
    }


    public void setCurrentCourse(Course currentCourse) {
        this.currentCourse = currentCourse;
    }


    public Professor getProf() {
        return prof;
    }


    public void setProf(Professor prof) {
        this.prof = prof;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public CourseInstance() {
    }

    public CourseInstance(Course currentCourse, Date startTime, Date endTime,
                          ArrayList<Student> students, Professor prof) {
        this.currentCourse = currentCourse;
        this.startTime = startTime;
        this.endTime = endTime;
        this.students = students;
        this.prof = prof;

    }


    public Date getStartTime() {
        return this.startTime;
    }


    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }


    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }


    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

}

