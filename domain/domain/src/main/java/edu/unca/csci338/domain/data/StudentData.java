package edu.unca.csci338.domain.data;

import edu.unca.csci338.domain.model.IDataChangeEvent;
import edu.unca.csci338.domain.model.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentData extends Data {

    private static List<IDataChangeEvent<Student>> studentChangedEvents = new ArrayList<IDataChangeEvent<Student>>();

    public Student getStudent(int ID) {
        ResultSet resultSet = getById("students", ID);
        Student student = null;
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int hoursCompleted = resultSet.getInt("hours_completed");
                int hoursCurrent = resultSet.getInt("hours_current");
                int hoursNeeded = resultSet.getInt("hours_needed");
                int yearLevel = resultSet.getInt("year");
                String status = resultSet.getString("status");
                student = new Student(id, firstName, lastName, hoursCompleted, hoursCurrent, hoursNeeded, yearLevel, status);

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return student;
    }

    public ArrayList<Student> getStudents() {
        ArrayList<Student> students = new ArrayList<Student>();
        ResultSet resultSet = getAll("students");
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int hoursCompleted = resultSet.getInt("hours_completed");
                int hoursCurrent = resultSet.getInt("hours_current");
                int hoursNeeded = resultSet.getInt("hours_needed");
                int yearLevel = resultSet.getInt("year");
                String status = resultSet.getString("status");
                Student student = new Student(id, firstName, lastName, hoursCompleted, hoursCurrent, hoursNeeded, yearLevel, status);
                students.add(student);

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //System.out.println(student.getID());
        return students;
    }

    //to insert students
    public void insertStudent(Student stud) {
        PreparedStatement prep = null;
        ResultSet res = null;
        int studId;
        try {
            prep = conn.prepareStatement("insert into students(first_name, last_name, hours_completed, hours_current, hours_needed, status, year) VALUES( ?,?,?,?,?,?,? )");
            prep.setString(1, stud.getFirstName());
            prep.setString(2, stud.getLastName());
            prep.setInt(3, stud.getHoursCompleted());
            prep.setInt(4, stud.getHoursCurrent());
            prep.setInt(5, stud.getHoursNeeded());
            prep.setString(6, stud.getStatus());
            prep.setInt(7, stud.getYearLevel());
            prep.executeUpdate();
            Student stud2 = getMostRecent();
            stud.setID(stud2.getID());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public Student getMostRecent() {
        ResultSet resultSet = getRecent("students");
        Student student = null;
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int hoursCompleted = resultSet.getInt("hours_completed");
                int hoursCurrent = resultSet.getInt("hours_current");
                int hoursNeeded = resultSet.getInt("hours_needed");
                int yearLevel = resultSet.getInt("year");
                String status = resultSet.getString("status");
                student = new Student(id, firstName, lastName, hoursCompleted, hoursCurrent, hoursNeeded, yearLevel, status);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //System.out.println(student.getID());
        return student;
    }

    public void updateStudent(Student stud) {
        PreparedStatement prep = null;
        try {
            prep = conn.prepareStatement("UPDATE students SET first_name = ?, last_name = ?, hours_completed = ?, hours_current = ?, hours_needed = ?, status = ?, year = ? WHERE id = ?");
            prep.setString(1, stud.getFirstName());
            prep.setString(2, stud.getLastName());
            prep.setInt(3, stud.getHoursCompleted());
            prep.setInt(4, stud.getHoursCurrent());
            prep.setInt(5, stud.getHoursNeeded());
            prep.setString(6, stud.getStatus());
            prep.setInt(7, stud.getYearLevel());
            prep.setInt(8, stud.getID());
            prep.executeUpdate();
            for (IDataChangeEvent<Student> listener : studentChangedEvents) {
                listener.onDataChanged(stud);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public static void AddOnStudentDataChangeEventListener(IDataChangeEvent<Student> listener) {
        studentChangedEvents.add(listener);
    }
}
