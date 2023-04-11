package edu.unca.csci338.domain.data;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.unca.csci338.domain.model.Course;

public class CourseData {
    private Connection conn = null;
//private Connection conn = null;


    public void Connect(String dbToConnectTo) {
        // auto close connection
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/" + dbToConnectTo, "root", "password123"); //
            if (conn != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
//	public Course getCourse(int ID) {
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		Course course = null;
//		
//		try {
//			preparedStatement = conn.prepareStatement("Select * from course_instances Where id=" + String.valueOf(ID));
//			 resultSet = preparedStatement.executeQuery();
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//
//            try {
//				while (resultSet.next()) {
//
//				    int id = resultSet.getInt("id");
//				    int number = resultSet.getInt("number");
//				    String name = resultSet.getString("name");
//				    String description = resultSet.getString("description");
//				    Department department = ((Department) resultSet).getDepartment("department");
//				    course = new Course(name, number, description, department, id);

    public Course getCourse(int ID) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Course course = null;
        try {
            preparedStatement = conn.prepareStatement("Select * from course_types Where id=" + String.valueOf(ID));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int number = resultSet.getInt("number");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int department_id = resultSet.getInt("department_id");
                course = new Course(name, number, description, id, department_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return course;
    }


    public void updateCourse(int ID, Course course) {
        PreparedStatement prep = null;
        try {
            prep = conn.prepareStatement("UPDATE course_types SET number = ?, name = ?, description = ?, department_id = ? WHERE id = ?");
            prep.setInt(1, course.getCourseReferenceNumber());
            prep.setString(2, course.getNameOfCourse());
            prep.setString(3, course.getDescription());
            prep.setInt(4, course.getDepartment());
            prep.setInt(5, ID);
            prep.executeUpdate();
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

    public void deleteCourse(int ID) {
        PreparedStatement prep = null;
        try {
            prep = conn.prepareStatement("DELETE from course_types WHERE id = ?");
            prep.setInt(1, ID);
            prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

