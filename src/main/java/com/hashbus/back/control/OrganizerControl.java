package com.hashbus.back.control;

import com.hashbus.back.database.data.access.PointDAO;
import com.hashbus.back.model.Bus;
import com.hashbus.back.model.Journey;
import com.hashbus.back.model.Point;
import com.hashbus.back.service.OrganizerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/organizer")
public class OrganizerControl {
    private OrganizerService organizerService;
    PointDAO pointDAO;
    // this is DONE
    @PostMapping("/addJourney")
    public ResponseEntity<Journey> addJourney(@RequestBody Journey journey) {
        Journey journey1 = organizerService.addJourney(journey);
        return new ResponseEntity<>(journey1, HttpStatus.CREATED);
    }

    //this is DONE
    @PostMapping("/editJourney")
    public ResponseEntity<Journey> editJourney(@RequestBody Journey journey) {
        Journey journey1 = organizerService.editJourney(journey);
        return ResponseEntity.ok(journey1); // Accepted
    }

    //TODO fix the loop
    @GetMapping("/viewJourney")
    public ResponseEntity<Journey> viewJourney(@RequestBody Journey journey) {
        Journey journey1 = organizerService.viewJourney(journey);
        return ResponseEntity.ok(journey1);
    }

    //TODO fix the loop
    @PostMapping("/deleteJourney")
    public ResponseEntity<Journey> deleteJourney(@RequestBody Journey journey) {
        organizerService.deleteJourney(journey);
        return ResponseEntity.ok(journey); // Deleted
    }

    // this is DONE
    @PostMapping("/AddPoint")
    public ResponseEntity<Boolean> addPoint(@RequestBody Point point) {
        organizerService.addPoint(point);
        return ResponseEntity.ok(true);
    }



    // this is DONE
    @GetMapping("/ViewAllPoint")
    public ResponseEntity<List<Point>> viewAllPoint() {
        List<Point> point = organizerService.viewAllPoint();
        return ResponseEntity.ok(point);
    }



}
