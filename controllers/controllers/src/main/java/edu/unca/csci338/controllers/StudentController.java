package edu.unca.csci338.controllers;

import edu.unca.csci338.domain.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import edu.unca.csci338.domain.data.StudentData;

@RestController
public class StudentController {

	private StudentData sd = new StudentData();

	
	public StudentController(){
		sd.Connect("edu_unca_csci338", "root", "password123");
		
	}
	
	@GetMapping("/students/")
	public ResponseEntity<ArrayList<Student>> Students() {
		//TODO Reference CSCS 338 project and Student object
		return ResponseEntity.ok(sd.getStudents());
	}

	@GetMapping("/student/{id}")
	public ResponseEntity<Student> Student(@PathVariable int id) {
		//TODO Reference CSCS 338 project and Student object
		return ResponseEntity.ok(sd.getStudent(id));
	}
	
	@GetMapping("/student/")
	public ResponseEntity<Student> Student() {
		return ResponseEntity.ok(sd.getMostRecent());
	}

	@PostMapping("/student/")
	public ResponseEntity<HttpStatus> updateStudent(
			@RequestBody(required = true) Student student
	) {
		sd.updateStudent(student);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	@PutMapping("/student/")
	public ResponseEntity<HttpStatus> addStudent(
			@RequestBody(required = true) Student student
	) {
		sd.insertStudent(student);;
		return ResponseEntity.ok(HttpStatus.OK);
	}
	@DeleteMapping("/student/{id}")
	public ResponseEntity<HttpStatus> deleteStudent(
			@PathVariable int id
	) {
		sd.delete(id, "students");
		return ResponseEntity.ok(HttpStatus.OK);
	}
}
