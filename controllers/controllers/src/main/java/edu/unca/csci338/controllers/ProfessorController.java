package edu.unca.csci338.controllers;

import java.util.ArrayList;

import edu.unca.csci338.domain.data.ProfessorData;
import edu.unca.csci338.domain.model.Professor;
import edu.unca.csci338.domain.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProfessorController {

	private ProfessorData pd = new ProfessorData();
	
	public ProfessorController(){
		pd.Connect("edu_unca_csci338", "root", "password123");

		
	}
	
	@GetMapping("/professors/")
	public ResponseEntity<ArrayList<Professor>> Professors() {
		//TODO Reference CSCS 338 project and Student object
		return ResponseEntity.ok(pd.getProfessors());
	}

	@GetMapping("/professor/{id}")
	public ResponseEntity<Professor> Professor(@PathVariable int id) {
		//TODO Reference CSCS 338 project and Student object
		return ResponseEntity.ok(pd.getProfessorByID(id));
	}

	@GetMapping("/professor/")
	public ResponseEntity<Professor> Professor() {
		return ResponseEntity.ok(pd.getMostRecent());
	}
	
	@PostMapping("/professor/")
	public ResponseEntity<HttpStatus> Professor(
			@RequestBody(required = true) Professor professor
	) {
		pd.updateProfessor(professor);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	@PutMapping("/professor/")
	public ResponseEntity<HttpStatus> addProfessor(
			@RequestBody(required = true) Professor professor
	) {
		pd.addProfessor(professor);;
		return ResponseEntity.ok(HttpStatus.OK);
	}
	@DeleteMapping("/professor/{id}")
	public ResponseEntity<HttpStatus> deleteProfessor(
			@PathVariable int id
	) {
		pd.delete(id, "professors");
		return ResponseEntity.ok(HttpStatus.OK);
	}
}

