package com.hashbus.back.control;

import com.hashbus.back.model.DataSchedule;
import com.hashbus.back.model.Point;
import com.hashbus.back.model.Schedule;
import com.hashbus.back.service.DriverService;
import com.hashbus.back.service.UserService;
import jnr.ffi.annotations.In;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Driver")
@AllArgsConstructor
public class DriverControl {
    private DriverService driverService;
    private UserService userService;

    @GetMapping("/GetScheduleData")
    public ResponseEntity<List<DataSchedule>> getDataSchedulesByBusId(@RequestParam Integer busId) {
        return ResponseEntity.ok(driverService.getSchedulesByBusId(busId));
    }

    @GetMapping("/GetAllPointsForJourney")
    public ResponseEntity<List<Point>> getAllPointsForJourney(@RequestParam Integer journeyId) {
        return ResponseEntity.ok(userService.getAllPointByJourneyId(journeyId));
    }

    @PostMapping("/UpdateNextPointIndex")
    public ResponseEntity<Boolean> updateNextPointIndexByScheduleId(
            @RequestParam Integer scheduleId,
            @RequestParam Integer previousIndex) {
        return ResponseEntity.ok(driverService.updateNextPointIndexByScheduleId(scheduleId, previousIndex));
    }

    @PostMapping("/setScheduleAsFinished")
    public ResponseEntity<Boolean> setScheduleAsFinished(@RequestParam Integer scheduleId) {
        return ResponseEntity.ok(driverService.setScheduleAsFinish(scheduleId));
    }

    @PostMapping("/UpdateLocation")
    public ResponseEntity<Boolean> updateLocation(
            @RequestParam Integer busId,
            @RequestParam Double latitude,
            @RequestParam Double longitude) {
        return ResponseEntity.ok(driverService.updateLocation(busId, latitude, longitude));
    }
}
