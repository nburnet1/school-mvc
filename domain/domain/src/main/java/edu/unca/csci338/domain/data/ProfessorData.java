package edu.unca.csci338.domain.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import edu.unca.csci338.domain.model.Professor;

public class ProfessorData {
	
	private Connection conn = null;
	
	public void Connect(String dbToConnectTo) {
        // auto close connection
        try 
        	{
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
	
	
	
	
	public ArrayList<Professor> getProfessors(){
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
			    int department = resultSet.getInt("department_id"); // need to change to int?
			    professor = new Professor( firstName, lastName, department);
				   
			    result.add(professor);
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        return result;
	}
	
	
	public ArrayList<Professor> getProfessorsByDepartment(int department_ID){
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
			    professor = new Professor( firstName, lastName, department);
				   
			    result.add(professor);
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        return result;
	}
	
	
	public Professor getProfessorByID(int ID){
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Professor professor = null;
		
		try {
			preparedStatement = conn.prepareStatement("select * from professors where id = " + ID);
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
			    professor = new Professor( firstName, lastName, department);
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
			preparedStatement = conn.prepareStatement("insert into professors (first_name, last_name, department_id) values ('"  +first + "', '" + last + "', " + department + ")");
			 profID = preparedStatement.executeUpdate();

			professor.setID(profID);


		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		}
		
		
	}
	
	public void updateProfessor(Professor professor) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String first = professor.getFirstName();
		String last = professor.getLastName();
		int department = professor.getDepartment();
		int ID = professor.getID();
		
	}
	
	public void deleteProfessor(int ID) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			preparedStatement = conn.prepareStatement("delete from professors where id = " + ID);
			 resultSet = preparedStatement.executeQuery();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	public void Disconnect() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

