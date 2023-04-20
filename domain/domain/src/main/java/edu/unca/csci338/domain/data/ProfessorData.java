package edu.unca.csci338.domain.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.unca.csci338.domain.model.IDataChangeEvent;
import edu.unca.csci338.domain.model.Professor;


public class ProfessorData extends Data {

    private static List<IDataChangeEvent<Professor>> professorChangedEvents = new ArrayList<IDataChangeEvent<Professor>>();


    public ArrayList<Professor> getProfessors() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Professor professor = null;
        ArrayList<Professor> result = new ArrayList<Professor>();
        try {
            preparedStatement = conn.prepareStatement("select * from professors");
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int department = resultSet.getInt("department_id");
                String status = resultSet.getString("status");
                boolean isHead = resultSet.getBoolean("is_head");
                professor = new Professor(id, firstName, lastName, department, status, isHead);
                result.add(professor);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }


    public ArrayList<Professor> getProfessorsByDepartment(int department_ID) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Professor professor = null;
        ArrayList<Professor> result = new ArrayList<Professor>();
        try {
            preparedStatement = conn.prepareStatement("select * from professors where department_id =" + department_ID);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int department = resultSet.getInt("department_id"); // need to change to int?
                professor = new Professor(id, firstName, lastName, department);
                result.add(professor);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }


    public Professor getProfessorByID(int ID) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Professor professor = null;
        try {
            preparedStatement = conn.prepareStatement("select * from professors where id = " + String.valueOf(ID));
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int department = resultSet.getInt("department_id");
                professor = new Professor(id, firstName, lastName, department);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return professor;
    }


    public void addProfessor(Professor professor) {
        PreparedStatement preparedStatement = null;
        int profID;
        String first = professor.getFirstName();
        String last = professor.getLastName();
        int department = professor.getDepartment();
        System.out.println(first);
        System.out.println(last);
        //int ID = professor.getID();
        try {
            preparedStatement = conn.prepareStatement("insert into professors (first_name, last_name, department_id) values ('" + first + "', '" + last + "', " + department + ")");
            profID = preparedStatement.executeUpdate();
            professor.setID(profID);


        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();

        }


    }

    public void updateProfessor(Professor professor) {
        PreparedStatement prep = null;
        try {
            prep = conn.prepareStatement("UPDATE professors SET first_name = ?, last_name = ?, department_id = ?, is_head = ? WHERE id = ?");
            prep.setString(1, professor.getFirstName());
            prep.setString(2, professor.getLastName());
            prep.setInt(3, professor.getDepartment());
            prep.setBoolean(4, professor.getHead());
            prep.setInt(5, professor.getID());
            prep.executeUpdate();
            for (IDataChangeEvent<Professor> listener : professorChangedEvents) {
                listener.onDataChanged(professor);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public Professor getMostRecent() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Professor professor = null;
        try {
            preparedStatement = conn.prepareStatement("Select * from professors Order by id DESC Limit 1");
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            while (resultSet.next()) {
                //System.out.println("Here");
                int id = resultSet.getInt("id");
                //System.out.println(id);
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int department = resultSet.getInt("department_id");
                String status = resultSet.getString("status");
                boolean is_head = resultSet.getBoolean("is_head");
                professor = new Professor(id, firstName, lastName, department, status, is_head);

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //System.out.println(student.getID());
        return professor;

    }

}

