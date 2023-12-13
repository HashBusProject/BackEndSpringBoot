package com.hashbus.back.control;

import com.google.gson.Gson;
import com.hashbus.back.database.mappers.TicketMapper;
import com.hashbus.back.model.*;
import com.hashbus.back.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/User")
@AllArgsConstructor
public class UserControl {
    private UserService userService;

    @PostMapping("/Login")
    public ResponseEntity<User> login(@RequestBody User user) {
        User user1 = userService.login(user);
        return ResponseEntity.ok(user1);
    }

    @GetMapping
    public String test() {
        return "test this fucking shit";
    }

    @GetMapping("/GetAllPoint")
    public ResponseEntity<List<Point>> getAllPoint() {
        List<Point> points = userService.getAllPoint();
        return ResponseEntity.ok(points);
    }

    @PostMapping("/SignUp")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        User result = userService.createUser(user);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/ChangePassword")
    public ResponseEntity<User> changePassword(@RequestBody User user) {
        User result = userService.changePassword(user);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/GetJourneysByPointId")
    public ResponseEntity<Set<Journey>> getJourneysByPointId(@RequestBody Point point) {
        Set<Journey> journeys = userService.getJourneysByPointId(point.getId());
        return ResponseEntity.ok(journeys);
    }

    @GetMapping("/GetScheduleByPointsAndTime")
    public ResponseEntity<List<SearchDataSchedule>> getScheduleByPointsAndTime(
            @RequestParam Integer startPointId,
            @RequestParam Integer endPointId,
            @RequestParam String time) {
        List<SearchDataSchedule> schedules = userService.getSearchData(startPointId, endPointId, time);
        return ResponseEntity.ok(schedules);
    }
    @GetMapping("/GetAllJournys")
    public ResponseEntity<List<Journey>> getAllJournys() {
        return ResponseEntity.ok(userService.getAllJournys());
    }
    @PostMapping("/GetPointById")
    public ResponseEntity<Point> getPointById(@RequestBody Point point){
        Point point1 = userService.getPointById(point.getId());
        return ResponseEntity.ok(point1) ;
    }

    @PostMapping("/GetJourneyById")
    public ResponseEntity<Journey> getJourneyById(@RequestBody Journey journey) {
        Journey journey1 = userService.getJourneyById(journey.getId());
        return ResponseEntity.ok(journey1) ;
    }
    @PostMapping ("/GetBusById")
    public ResponseEntity<Bus> getBusById(@RequestBody Bus bus){
        Bus bus1 = userService.getBusById(bus.getId());
        return ResponseEntity.ok(bus1);
    }
    @PostMapping("/GetTicketsByUserId")
    public ResponseEntity<List<Ticket>> getTicketsByUserId(@RequestBody User user) {
        List <Ticket> tickets = userService.getTicketsByUserId(user.getUserID()) ;
        return ResponseEntity.ok(tickets);
    }
    @PostMapping("/BuyTicket")
    public ResponseEntity<Boolean> buyTicket(
            @RequestParam Integer userId ,
            @RequestParam Integer journeyId
    ){
        return ResponseEntity.ok(userService.buyTicket(userId , journeyId));
    }
}
