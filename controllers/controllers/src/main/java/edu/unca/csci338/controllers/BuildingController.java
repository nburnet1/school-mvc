package edu.unca.csci338.controllers;

import edu.unca.csci338.domain.data.BuildingData;
import edu.unca.csci338.domain.model.Building;
import edu.unca.csci338.domain.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import edu.unca.csci338.domain.data.StudentData;

@RestController
public class BuildingController {
    private BuildingData bd = new BuildingData();

    public BuildingController() {
        bd.Connect("edu_unca_csci338", "root", "password123");

    }

    @GetMapping("/buildings/")
    public ResponseEntity<ArrayList<Building>> Buildings() {
        return ResponseEntity.ok(bd.getBuildings());
    }

    @GetMapping("/building/{id}")
    public ResponseEntity<Building> Building(@PathVariable int id) {
        return ResponseEntity.ok(bd.getBuilding(id));
    }

}
