package com.hashbus.back.control;

import com.hashbus.back.model.Bus;
import com.hashbus.back.model.Point;
import com.hashbus.back.model.User;
import com.hashbus.back.service.AdminService;
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

    // This is done
    @PostMapping("/AddUser")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User user1 = adminService.addUser(user);
        return new ResponseEntity<>(user1 , HttpStatus.CREATED);
    }
    // This is done
    @PostMapping("/DeleteUser")
    public ResponseEntity<User> deleteUser (@RequestBody User user){
        User user1 = adminService.deleteUser(user)  ;
        return  ResponseEntity.ok(user1);
    }

    // This is Done
    @PostMapping("AddStopPoint")
    public ResponseEntity<Point> addStopPoint(@RequestBody Point point){
        Point point1 = adminService.addStopPoint(point) ;
        return new ResponseEntity<>(point1 , HttpStatus.CREATED);
    }
    // This is Done
    @PostMapping("AddBus")
    public ResponseEntity<Bus> addBus(@RequestBody Bus bus){
        Bus bus1 = adminService.addBus(bus) ;
        return new ResponseEntity<>(bus1 , HttpStatus.CREATED) ;
    }

    @PostMapping("DeleteBusDriver")
    public ResponseEntity<User> deleteBusDriver(@RequestBody User user){
        User user1 = adminService.deleteBusDriver(user) ;
        return ResponseEntity.ok(user1) ;
    }
    @GetMapping ("/GetUser")
    public ResponseEntity<List<User>> getUser(@RequestParam int role){ {
        List<User> user =  adminService.getUser(role) ;
        return ResponseEntity.ok(user) ;
    }
    }
}
