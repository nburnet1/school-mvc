package edu.unca.csci338.domain.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.unca.csci338.domain.model.IStudentDataChangedEvent;
import edu.unca.csci338.domain.model.Student;

public class StudentData {
	private Connection conn = null;
	public boolean connected=false;
	
	private static List<IStudentDataChangedEvent> studentChangedEvents=new ArrayList<IStudentDataChangedEvent>();

	public void Connect(String dbToConnectTo, String username, String pass) {
        // auto close connection
        try 
        	{
        		conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/" + dbToConnectTo, username, pass); //
        			
	            if (conn != null) {
	                System.out.println("Connected to the database!");
	                connected = true;
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
	
	public Student getStudent(int ID) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Student student = null;
		
		try {
			preparedStatement = conn.prepareStatement("Select * from students Where id = " + String.valueOf(ID));
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
	
	public ArrayList<Student> getStudents() {
		ArrayList<Student> students = new ArrayList<Student>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
				
		try {
			preparedStatement = conn.prepareStatement("Select * from students");
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
		PreparedStatement prep=null;
		ResultSet res=null;
		int studId;
		
		try {
			prep=conn.prepareStatement("insert into students(first_name, last_name, hours_completed, hours_current, hours_needed, status, year) VALUES( ?,?,?,?,?,?,? )");
			
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
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Student student = null;
		
		try {
			preparedStatement = conn.prepareStatement("Select * from students Order by id DESC Limit 1");
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
	
//	public void insertStudent(Student stud) {
//		PreparedStatement prep=null;
//		ResultSet res=null;
//		int studId;
//		
//		try {
//			prep=conn.prepareStatement("insert into students(id, first_name, last_name, hours_completed, hours_current, hours_needed, status, year) VALUES( ?,?,?,?,?,?,?,? )");
//			prep.setInt(1, stud.getID());
//			prep.setString(2, stud.getFirstName());
//			prep.setString(3, stud.getLastName());
//			prep.setInt(4, stud.getHoursCompleted());
//			prep.setInt(5, stud.getHoursCurrent());
//			prep.setInt(6, stud.getHoursNeeded());
//			prep.setString(7, stud.getStatus());
//			prep.setInt(8, stud.getYearLevel());
//			prep.executeUpdate();
//			
//			//studId=prep.executeUpdate();
//			
//			
//			//stud.setID(studId);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	
	public void updateStudent(Student stud) {
		PreparedStatement prep=null;
		
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
			
			for(IStudentDataChangedEvent listener : studentChangedEvents) {
				listener.onStudentDataChanged(stud);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
//	public void updateStudent(Student stud) {
//		PreparedStatement prep=null;
//		
//		try {
//			prep = conn.prepareStatement("UPDATE students SET id = ?, first_name = ?, last_name = ?, hours_completed = ?, hours_current = ?, hours_needed = ?, status = ?, year = ? WHERE id = ?");
//			prep.setInt(1, stud.getID());
//			prep.setString(2, stud.getFirstName());
//			prep.setString(3, stud.getLastName());
//			prep.setInt(4, stud.getHoursCompleted());
//			prep.setInt(5, stud.getHoursCurrent());
//			prep.setInt(6, stud.getHoursNeeded());
//			prep.setString(7, stud.getStatus());
//			prep.setInt(8, stud.getYearLevel());
//			prep.setInt(9, stud.getID());
//			prep.executeUpdate();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	
	
	
	public void deleteStudent(int ID) {
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = conn.prepareStatement("DELETE FROM students WHERE id = ?" );
			preparedStatement.setInt(1, ID);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void Disconnect() {
		try {
			conn.close();
			connected=false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean getConnectStatus() {
		return connected;
	}
	
	public static void AddOnStudentDataChangeEventListner(IStudentDataChangedEvent listener) {
		studentChangedEvents.add(listener);
	}
	
	//public int
}
