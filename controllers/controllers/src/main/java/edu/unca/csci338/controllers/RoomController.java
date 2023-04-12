package edu.unca.csci338.controllers;

import edu.unca.csci338.domain.model.Room;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import edu.unca.csci338.domain.data.RoomData;

@RestController
public class RoomController {

    private RoomData rd = new RoomData();


    public RoomController(){
        rd.Connect("edu_unca_csci338", "root", "password123");

    }

    @GetMapping("/rooms/")
    public ResponseEntity<ArrayList<Room>> Rooms() {
        //TODO Reference CSCS 338 project and Student object
        return ResponseEntity.ok(rd.getRooms());
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<Room> Room(@PathVariable int id) {
        //TODO Reference CSCS 338 project and Student object
        return ResponseEntity.ok(rd.getRoom(id));
    }


    @PostMapping("/room/")
    public ResponseEntity<HttpStatus> updateRoom(
            @RequestBody(required = true) Room room
    ) {
        rd.updateRoom(room);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @DeleteMapping("/room/{id}")
    public ResponseEntity<HttpStatus> deleteRoom(
            @PathVariable int id
    ) {
        Room existingRoom = rd.getRoom(id);
        if (existingRoom == null) {
            return ResponseEntity.notFound().build();
        }
        rd.deleteRoom(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @PutMapping("/room/")
    public ResponseEntity<HttpStatus> createRoom(@RequestBody Room room) {
        rd.insertRoom(room);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}