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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/organizer")
public class OrganizerControl {
    private OrganizerService organizerService;
    PointDAO pointDAO;
    @GetMapping("/addJourney")
    public ResponseEntity<Journey> addJourney(Journey journey) {
        Journey journey1 = organizerService.addJourney(journey);
        return new ResponseEntity<>(journey1, HttpStatus.CREATED);
    }

    @GetMapping("/editJourney")
    public ResponseEntity<Journey> editJourney(Journey journey) {
        Journey journey1 = organizerService.editJourney(journey);
        return ResponseEntity.ok(journey1); // Accepted
    }

    @GetMapping("/viewJourney")
    public ResponseEntity<Journey> viewJourney(Journey journey) {
        Journey journey1 = organizerService.viewJourney(journey);
        return ResponseEntity.ok(journey1);
    }

    @GetMapping("/deleteJourney")
    public ResponseEntity<Journey> deleteJourney(Journey journey) {
        organizerService.deleteJourney(journey);
        return ResponseEntity.ok(journey); // Deleted
    }

    @GetMapping("/addPoint")
    public ResponseEntity<Boolean> addPoint(Point point) {
        organizerService.addPoint(point);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/viewAllPoint")
    public ResponseEntity<List<Point>> viewAllPoint() {
        List<Point> point = organizerService.viewAllPoint();
        return ResponseEntity.ok(point);
    }

    @GetMapping("/test")
    public ResponseEntity<Point> getPointTest(@RequestBody Point point){
        Point point1 = pointDAO.getPointById(point.getId());
        return ResponseEntity.ok(point1);
    }
}
