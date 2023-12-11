package com.hashbus.back.control;

import com.hashbus.back.model.Bus;
import com.hashbus.back.model.Point;
import com.hashbus.back.model.User;
import com.hashbus.back.service.AdminService;
import jnr.ffi.annotations.In;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/Admin")
public class AdminControl {
    AdminService adminService;

    @PostMapping("/Login")
    public ResponseEntity<User> login(@RequestBody User user)  {
        User user1 =adminService.login(user) ;
        return ResponseEntity.ok(user1) ;
    }

    // This is Done
    @PostMapping("/AddUser")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User user1 = adminService.addUser(user);
        return new ResponseEntity<>(user1, HttpStatus.CREATED);
    }

    // This is Done
    @PostMapping("/DeleteUser")
    public ResponseEntity<Integer> deleteUser(@RequestBody User user) {
        int id1 = adminService.deleteUser(user.getUserID());
        return ResponseEntity.ok(id1);
    }

    @PostMapping("/DeletePoint")
    public ResponseEntity<Boolean> deletePoint(@RequestBody Point point) {
        if (adminService.deletePoint(point.getId())) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }

    @PostMapping("/AddStopPoint")
    public ResponseEntity<Point> addStopPoint(@RequestBody Point point) {
        Point point1 = adminService.addStopPoint(point);
        return new ResponseEntity<>(point1, HttpStatus.CREATED);
    }

    @PostMapping("/AddBus")
    public ResponseEntity<Bus> addBus(@RequestBody Bus bus) {
        Bus bus1 = adminService.addBus(bus);
        return new ResponseEntity<>(bus1, HttpStatus.CREATED);
    }

    //This is Done
    @GetMapping("/GetUser")
    public ResponseEntity<List<User>> getUser(@RequestParam int role) {
        List<User> user = adminService.getUser(role);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/GetNumberOfUserByRole")
    public ResponseEntity<Integer> getNumberOfUserByRole(@RequestParam int role) {
        int numberOfUser = adminService.getNumberOfUserByRole(role);
        return ResponseEntity.ok(numberOfUser);
    }

    @PostMapping("/EditUser")
    public ResponseEntity<Boolean> editUser(@RequestBody User user) {
        if (adminService.editUser(user) > 0) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

    @GetMapping("/GetAllPoint")
    public ResponseEntity<List<Point>> GetAllPoint() {
        return ResponseEntity.ok(adminService.getAllPoint());
    }

    @GetMapping("/GetNumberOfPoint")
    public ResponseEntity<Integer> getNumberOfPoint() {
        return ResponseEntity.ok(adminService.getNumberOfPoint());
    }

    @GetMapping("/GetAllBuses")
    public ResponseEntity<List<Bus>> getAllBuses() {
        return ResponseEntity.ok(adminService.getAllBuses());
    }

    @GetMapping("/GetNumberOfBuses")
    public ResponseEntity<Integer> getNumberOfBuses() {
        return ResponseEntity.ok(adminService.getNumberOfBuses());
    }

    @PostMapping("/DeleteBus")
    public ResponseEntity<Boolean> deleteBus(@RequestBody Bus bus) {
        if (adminService.deleteBus(bus.getId())) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }
    @PostMapping("/EditBus")
    public ResponseEntity<Boolean> editBus(@RequestBody Bus bus){
        return ResponseEntity.ok(adminService.editBus(bus)) ;
    }
    @GetMapping("/GetNumberOfUser")
    public ResponseEntity<Integer> getNumberOfUser(){
        return ResponseEntity.ok(adminService.getNumberOfUser());
    }
}