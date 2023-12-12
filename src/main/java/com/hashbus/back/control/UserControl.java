package com.hashbus.back.control;

import com.google.gson.Gson;
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

}
