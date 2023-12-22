package com.hashbus.back.control;

import com.google.gson.Gson;
import com.hashbus.back.database.mappers.TicketMapper;
import com.hashbus.back.model.*;
import com.hashbus.back.service.UserService;
import jnr.ffi.annotations.In;
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

    @GetMapping("/GetPointById")
    public ResponseEntity<Point> getPointById(@RequestParam Integer pointId) {
        Point point1 = userService.getPointById(pointId);
        return ResponseEntity.ok(point1);
    }

    @GetMapping("/GetJourneyById")
    public ResponseEntity<Journey> getJourneyById(@RequestParam Integer journeyId) {
        Journey journey1 = userService.getJourneyById(journeyId);
        return ResponseEntity.ok(journey1);
    }

    @GetMapping("/GetBusById")
    public ResponseEntity<Bus> getBusById(@RequestParam Integer busId) {
        Bus bus1 = userService.getBusById(busId);
        return ResponseEntity.ok(bus1);
    }

    @GetMapping("/GetTicketsByUserId")
    public ResponseEntity<List<Ticket>> getTicketsByUserId(@RequestParam Integer userId) {
        List<Ticket> tickets = userService.getTicketsByUserId(userId);
        return ResponseEntity.ok(tickets);
    }

    @PostMapping("/BuyTicket")
    public ResponseEntity<Boolean> buyTicket(
            @RequestParam Integer userId,
            @RequestParam Integer journeyId) {
        return ResponseEntity.ok(userService.buyTicket(userId, journeyId));
    }

    @GetMapping("/AllPointByJourneyId")
    public ResponseEntity<List<Point>> AllPointByJourneyId(@RequestParam Integer journeyId) {
        List<Point> list = userService.getAllPointByJourneyId(journeyId);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/ConfirmRide")
    public ResponseEntity<Boolean> confirmRide(
            @RequestParam("bus") Integer busId,
            @RequestParam("user") Integer userId,
            @RequestParam("journey") Integer journeyId,
            @RequestBody Schedule schedule) {
        boolean x = userService.confirmRide(userId, journeyId, busId, schedule);
        return ResponseEntity.ok(x);
    }

}