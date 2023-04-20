package edu.unca.csci338.controllers;

import edu.unca.csci338.domain.data.CourseData;
import edu.unca.csci338.domain.model.Course;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class CourseController {
    private CourseData cd = new CourseData();

    public CourseController() {
        cd.Connect("edu_unca_csci338", "root", "password123");
    }

    @GetMapping("/courses/")
    public ResponseEntity<ArrayList<Course>> Courses() {
        return ResponseEntity.ok(cd.getCourses());
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<Course> Course(@PathVariable int id) {
        return ResponseEntity.ok(cd.getCourse(id));
    }

    @PostMapping("/course/")
    public ResponseEntity<Course> updateCourse(@RequestBody Course course) {
        cd.updateCourse(course);
        return ResponseEntity.ok(course);
    }
    @PutMapping("/course/")
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        cd.createCourse(course);
        return ResponseEntity.ok(course);
    }
    @DeleteMapping("/course/{id}")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable int id) {
        cd.delete(id,"course_type");
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
