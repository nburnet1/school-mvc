package edu.unca.csci338.domain.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import edu.unca.csci338.domain.model.*;



public class CourseInstanceData {
	
	
	private Connection conn = null;
	
	
	public void Connect(String dbToConnectTo) {
        // auto close connection
        try 
        	{
        		conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/" + dbToConnectTo, "root", "Treacles!1"); //
        			
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
		CourseInstance Course = null;
		
		try {
			preparedStatement = conn.prepareStatement("Select * from course_instances Where id =" + String.valueOf(ID));
			 
			resultSet = preparedStatement.executeQuery();
			 
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

            try {
				while (resultSet.next()) {
					
				    //Course = new CourseInstance(c,  t,  s, p);
				    CourseInstance course = new CourseInstance();
				    
				    Professor prof = new Professor();
				    
				    course.setID(resultSet.getInt("id"));
				    course.setStartTime(resultSet.getDate("start_time"));
				    course.setEndTime(resultSet.getDate("end_time"));
					
				    int professor_id  = resultSet.getInt("professor_id");
				    
				    profPS = conn.prepareStatement("Select * from professor Where id =" + String.valueOf(professor_id));
					profRS = preparedStatement.executeQuery();
				    
					prof.setFirstName(profRS.getString("first_name"));
				    
					course.setProf(prof);
				    
				    

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            finally{
            	
            }
            return Course;

	}
	
	public int deleteCourseInstance(int ID) {
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = conn.prepareStatement("DELETE FROM course_instances WHERE id =" + String.valueOf(ID));
			 preparedStatement.executeQuery();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		 try {
			
		int rowsDeleted = preparedStatement.executeUpdate();
		if (rowsDeleted > 0) {
		return 1;
		}else {
		System.out.println("A user was deleted successfully!");
		return 0;
		}
		
			 
		
		 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 return 0;
		
	}
	
	public int createCourseInstance(Course icourse, int iprofessor, Room iroom) {
		PreparedStatement preparedStatement = null;
	
		
		
		 
		Random rand = new Random(); 
		int int_random = rand.nextInt(999999);
		CourseInstance t = getCourseInstance(int_random);
		if(t == null) {
			createCourseInstance(icourse, iprofessor, iroom);
		}
		
		
		
		try {
			String sql = "INSERT INTO course_instances (id , type_id , professor_id , room_id) VALUES (?, ?, ?, ?)";
			 
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, int_random);
			statement.setInt(2, icourse.getId());
			statement.setInt(3, iprofessor);
			statement.setInt(4, iroom.getId());
			 
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
			    System.out.println("A new user was inserted successfully!");
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return 1;
		
		
	}
	
	}

		
		
	



