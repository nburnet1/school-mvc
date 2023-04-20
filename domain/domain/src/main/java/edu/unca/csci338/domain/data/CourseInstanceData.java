package edu.unca.csci338.domain.data;

import edu.unca.csci338.domain.model.CourseInstance;
import edu.unca.csci338.domain.model.IDataChangeEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CourseInstanceData extends Data {
    private static List<IDataChangeEvent<CourseInstance>> courseInstanceChangedEvents = new ArrayList<IDataChangeEvent<CourseInstance>>();


    public CourseInstance getCourseInstance(int ID) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CourseInstance course = null;

        try {
            preparedStatement = conn.prepareStatement("Select * from course_instances Where id =" + String.valueOf(ID));
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                int courseId = resultSet.getInt("type_id");
                int profId = resultSet.getInt("professor_id");
                int roomId = resultSet.getInt("room_id");
                int start = resultSet.getInt("start_time");
                int end = resultSet.getInt("end_time");
                course = new CourseInstance(id, courseId, profId, roomId, start, end);


            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return course;

    }


    public ArrayList<CourseInstance> getCourseInstances() {
        ArrayList<CourseInstance> courses = new ArrayList<CourseInstance>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = conn.prepareStatement("Select * from course_instances");
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int courseId = resultSet.getInt("type_id");
                int profId = resultSet.getInt("professor_id");
                int roomId = resultSet.getInt("room_id");
                int start = resultSet.getInt("start_time");
                int end = resultSet.getInt("end_time");
                CourseInstance course = new CourseInstance(id, courseId, profId, roomId, start, end);
                courses.add(course);

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //System.out.println(student.getID());
        return courses;
    }


    public CourseInstance getMostRecent() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CourseInstance course = null;

        try {
            preparedStatement = conn.prepareStatement("Select * from course_instances Order by id DESC Limit 1");
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int courseId = resultSet.getInt("type_id");
                int profId = resultSet.getInt("professor_id");
                int roomId = resultSet.getInt("room_id");
                int start = resultSet.getInt("start_time");
                int end = resultSet.getInt("end_time");
                course = new CourseInstance(id, courseId, profId, roomId, start, end);

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //System.out.println(student.getID());
        return course;
    }


    public void updateCourseInstance(CourseInstance course) {
        PreparedStatement prep = null;

        try {
            prep = conn.prepareStatement("UPDATE course_instances SET type_id = ?, professor_id = ?, room_id = ?, start_time = ?, end_time = ? WHERE id = ?");
            prep.setInt(1, course.getCurrentCourse());
            prep.setInt(2, course.getProf());
            prep.setInt(3, course.getRoomId());
            prep.setInt(4, course.getStartTime());
            prep.setInt(5, course.getEndTime());
            prep.setInt(6, course.getID());
            prep.executeUpdate();

            for (IDataChangeEvent<CourseInstance> listener : courseInstanceChangedEvents) {
                listener.onDataChanged(course);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public void insertCourseInstance(CourseInstance course) {
        PreparedStatement prep = null;
        ResultSet res = null;

        try {
            prep = conn.prepareStatement("insert into course_instances(type_id, professor_id, room_id, start_time, end_time) VALUES( ?,?,?,?,? )");

            prep.setInt(1, course.getCurrentCourse());
            prep.setInt(2, course.getProf());
            prep.setInt(3, course.getRoomId());
            prep.setInt(4, course.getStartTime());
            prep.setInt(5, course.getEndTime());

            prep.executeUpdate();


            CourseInstance course2 = getMostRecent();
            course.setID(course2.getID());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}

