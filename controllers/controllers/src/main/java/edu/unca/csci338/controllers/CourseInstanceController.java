package edu.unca.csci338.controllers;

import edu.unca.csci338.domain.data.CourseInstanceData;
import edu.unca.csci338.domain.model.CourseInstance;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

@RestController
public class CourseInstanceController {
    private CourseInstanceData cid = new CourseInstanceData();

    public CourseInstanceController(){
        cid.Connect("edu_unca_csci338", "root", "password123");
    }

    @GetMapping("/courseInstances/")
    public ResponseEntity<ArrayList<CourseInstance>> CourseInstances() {
        return ResponseEntity.ok(cid.getCourseInstances());
    }

    @GetMapping("/courseInstance/{id}")
    public ResponseEntity<CourseInstance> CourseInstance(@PathVariable int id) {
        return ResponseEntity.ok(cid.getCourseInstance(id));
    }

    @PostMapping("/courseInstance/")
    public ResponseEntity<CourseInstance> updateCourseInstance(@RequestBody CourseInstance courseInstance) {
        cid.updateCourseInstance(courseInstance);
        return ResponseEntity.ok(courseInstance);
    }

    @PutMapping("/courseInstance/")
    public ResponseEntity<CourseInstance> addCourseInstance(@RequestBody CourseInstance courseInstance) {
        cid.createCourseInstance(courseInstance);
        return ResponseEntity.ok(courseInstance);
    }

    @DeleteMapping("/courseInstance/{id}")
    public ResponseEntity<HttpStatus> deleteCourseInstance(@PathVariable int id) {
        cid.deleteCourseInstance(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }



}
