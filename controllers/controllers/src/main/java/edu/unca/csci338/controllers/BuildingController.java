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

    @PostMapping("/building/")
    public ResponseEntity<Building> updateBuilding(@RequestBody Building building) {
        bd.updateBuilding(building);
        return ResponseEntity.ok(building);
    }
    @PutMapping("/building/")
    public ResponseEntity<Building> addBuilding(@RequestBody Building building) {
        bd.addBuilding(building);
        return ResponseEntity.ok(building);
    }
    @DeleteMapping("/building/{id}")
    public ResponseEntity<HttpStatus> deleteBuilding(@PathVariable int id) {
        bd.delete(id,"buildings");
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
