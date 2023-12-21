package com.hashbus.back.control;

import com.hashbus.back.database.data.access.PointDAO;
import com.hashbus.back.database.data.access.TicketDAO;
import com.hashbus.back.model.*;
import com.hashbus.back.service.OrganizerService;
import jnr.ffi.annotations.In;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Organizer")
public class OrganizerControl {
    private OrganizerService organizerService;
    PointDAO pointDAO;

    @PostMapping("/AddJourney")
    public ResponseEntity<Journey> addJourney(@RequestBody Journey journey) {
        Journey journey1 = organizerService.addJourney(journey);
        return new ResponseEntity<>(journey1, HttpStatus.CREATED);
    }

    @PostMapping("/EditJourney")
    public ResponseEntity<Journey> editJourney(@RequestBody Journey journey) {
        Journey journey1 = organizerService.editJourney(journey);
        return ResponseEntity.ok(journey1); // Accepted
    }

    @GetMapping("/GetAllJourneys")
    public ResponseEntity<List<Journey>> getAllJourneys() {
        List<Journey> journeys = organizerService.getAllJourneys();
        return ResponseEntity.ok(journeys);
    }

    @PostMapping("/DeleteJourney")
    public ResponseEntity<Journey> deleteJourney(@RequestBody Journey journey) {
        organizerService.deleteJourney(journey);
        return ResponseEntity.ok(journey); // Deleted
    }

    @PostMapping("/AddPoint")
    public ResponseEntity<Boolean> addPoint(@RequestBody Point point) {
        organizerService.addPoint(point);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/ViewAllPoint")
    public ResponseEntity<List<Point>> viewAllPoint() {
        List<Point> point = organizerService.viewAllPoint();
        return ResponseEntity.ok(point);
    }

    @GetMapping("/GetNumberOfJourneys")
    public ResponseEntity<Integer> getNumberOfJourneys() {
        return ResponseEntity.ok(organizerService.getNumberOfJourneys());
    }

    @GetMapping("/GetNumberOfTickets")
    public ResponseEntity<Integer> getNumberOfTickets() {
        return ResponseEntity.ok(organizerService.getNumberOfTickets());
    }

    @GetMapping("/GetAllTickets")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(organizerService.getAllTickets());
    }

    @PostMapping("/AddStopPoint")
    public ResponseEntity<Boolean> addStopPoint(@RequestParam Integer pointId,
                                                @RequestParam Integer journeyId,
                                                @RequestParam Integer index) {
        return ResponseEntity.ok(organizerService.addStopPoint(pointId, journeyId, index));
    }

    @GetMapping("/GetNameOfPoint")
    public ResponseEntity<String> getNameOfPoint(@RequestParam int pointId) {
        return ResponseEntity.ok(organizerService.getNameOfPoint(pointId));
    }

    @DeleteMapping("/DeleteAllTickets")
    public ResponseEntity<Boolean> deleteAllTickets(){
        return ResponseEntity.ok(organizerService.deleteAllTickets()) ;
    }

    @GetMapping("/GetAllSchedule")
    public ResponseEntity<List<Schedule>> getAllSchedule() {
        return ResponseEntity.ok(organizerService.getAllSchedule());
    }

    @GetMapping("/GetNumberOfSchedules")
    public ResponseEntity<Integer> getNumberOfSchedules(){
        return ResponseEntity.ok(organizerService.getNumberOfSchedules());
    }

    @GetMapping("/GetNameOfJourneyById")
    public ResponseEntity<String> getNameOfJourneyById(@RequestParam Integer journeyId){
        return ResponseEntity.ok(organizerService.getNameOfJourneyById(journeyId)) ;
    }

    @DeleteMapping("/DeleteSchedule")
    public ResponseEntity<Boolean> deleteSchedule(@RequestParam Integer scheduleId){
        return ResponseEntity.ok(organizerService.deleteSchedule(scheduleId)) ;
    }
}
