package edu.unca.csci338.controllers;

import java.util.ArrayList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Professor {
	
	public Professor(){
		
	}
	
	@GetMapping("/professors/")
	public ResponseEntity<ArrayList<Professor>> Students() {
		//TODO Reference CSCS 338 project and Student object
		return null;
	}

	@GetMapping("/professor/{id}")
	public ResponseEntity<Professor> Student(@PathVariable int id) {
		//TODO Reference CSCS 338 project and Student object
		return null;
	}
	
	@PostMapping("/professor/")
	public ResponseEntity<Professor> Student() {
		return null;
	}
}

