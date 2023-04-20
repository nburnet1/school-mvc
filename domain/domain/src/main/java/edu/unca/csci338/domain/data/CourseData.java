package edu.unca.csci338.domain.data;


import edu.unca.csci338.domain.model.Course;
import edu.unca.csci338.domain.model.IDataChangeEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseData extends Data {


    private static List<IDataChangeEvent<Course>> courseChangedEvents = new ArrayList<IDataChangeEvent<Course>>();

    public ArrayList<Course> getCourses() {

        ResultSet resultSet = getAll("course_types");
        Course course = null;
        ArrayList<Course> result = new ArrayList<Course>();

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int course_reference_number = resultSet.getInt("number");
                String description = resultSet.getString("description");
                int department_id = resultSet.getInt("department_id");
                course = new Course(name, course_reference_number, description, department_id, id);

                result.add(course);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public Course getCourse(int ID) {


        ResultSet resultSet = getById("course_types", ID);
        Course course = null;

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int number = resultSet.getInt("number");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int department_id = resultSet.getInt("department_id");
                course = new Course(name, number, description, department_id, id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return course;
    }


    public void updateCourse(Course course) {
        PreparedStatement prep = null;

        try {
            prep = conn.prepareStatement("UPDATE course_types SET number = ?, name = ?, description = ?, department_id = ? WHERE id = ?");
            prep.setInt(1, course.getCourseReferenceNumber());
            prep.setString(2, course.getNameOfCourse());
            prep.setString(3, course.getDescription());
            prep.setInt(4, course.getDepartment());
            prep.setInt(5, course.getId());
            prep.executeUpdate();

            for (IDataChangeEvent<Course> listener : courseChangedEvents) {
                listener.onDataChanged(course);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public void createCourse(Course course) {
        PreparedStatement prep = null;
        int courseID;
        try {
            prep = conn.prepareStatement("INSERT INTO course_types (number, name, description, department_id) VALUES (?,?,?,?)");
            prep.setInt(1, course.getCourseReferenceNumber());
            prep.setString(2, course.getNameOfCourse());
            prep.setString(3, course.getDescription());
            prep.setInt(4, course.getDepartment());

            courseID = prep.executeUpdate();

            course.setId(courseID);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
