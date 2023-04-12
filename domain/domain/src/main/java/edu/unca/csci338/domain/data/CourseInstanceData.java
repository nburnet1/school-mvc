package edu.unca.csci338.domain.data;


import edu.unca.csci338.domain.model.CourseInstance;
import edu.unca.csci338.domain.model.IDataChangeEvent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CourseInstanceData {


    private Connection conn = null;

    private static List<IDataChangeEvent<CourseInstance>> courseInstanceChangedEvents = new ArrayList<IDataChangeEvent<CourseInstance>>();

    public CourseInstanceData(){

    }


    public void Connect(String dbToConnectTo, String username, String pass) {
        // auto close connection
        try
        {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/" + dbToConnectTo, username, pass); //
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

    public CourseInstance getCourseInstance(int ID) {
        PreparedStatement preparedStatement = null;
        PreparedStatement profPS = null;
        ResultSet resultSet = null;
        ResultSet profRS = null;
        CourseInstance Course = new CourseInstance();
        try {
            preparedStatement = conn.prepareStatement("Select * from course_instances Where id =" + String.valueOf(ID));
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            while (resultSet.next()) {
                Course.setId(resultSet.getInt("id"));
                Course.setRoomId(resultSet.getInt("room_id"));
                Course.setTypeId(resultSet.getInt("type_id"));
                Course.setProfessorId(resultSet.getInt("professor_id"));
                ;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Course;

    }

    public ArrayList<CourseInstance> getCourseInstances(){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CourseInstance courseInstance = new CourseInstance();
        ArrayList<CourseInstance> result = new ArrayList<CourseInstance>();

        try {
            preparedStatement = conn.prepareStatement("select * from course_instances");
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int typeId = resultSet.getInt("type_id");
                int professorId = resultSet.getInt("professor_id");
                int roomId = resultSet.getInt("room_id");

                courseInstance = new CourseInstance(id, typeId, professorId, roomId);

                result.add(courseInstance);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public void deleteCourseInstance(int ID) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement("set foreign_key_checks = off");
            preparedStatement = conn.prepareStatement("DELETE FROM course_instances WHERE id =" + String.valueOf(ID));
            preparedStatement = conn.prepareStatement("set foreign_key_checks = on");
            preparedStatement.executeQuery();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void createCourseInstance(CourseInstance course) {
        PreparedStatement preparedStatement = null;
        try {
            String sql = "INSERT INTO course_instances (type_id , professor_id , room_id) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, course.getTypeId());
            statement.setInt(2, course.getProfessorId());
            statement.setInt(3, course.getRoomId());
            int id = statement.executeUpdate();
            course.setId(id);

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    public void updateCourseInstance(CourseInstance courseInstance) {
        PreparedStatement prep = null;

        try {
            prep = conn.prepareStatement("UPDATE course_instances SET type_id = ?, professor_id = ?, room_id = ? WHERE id = ?");
            prep.setInt(1, courseInstance.getTypeId());
            prep.setInt(2, courseInstance.getProfessorId());
            prep.setInt(3, courseInstance.getRoomId());
            prep.setInt(4, courseInstance.getId());
            prep.executeUpdate();
            for (IDataChangeEvent<CourseInstance> listener : courseInstanceChangedEvents) {
                listener.onDataChanged(courseInstance);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

}







