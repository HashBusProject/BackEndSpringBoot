package com.hashbus.back.control;

import com.hashbus.back.model.Point;
import com.hashbus.back.model.User;
import com.hashbus.back.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    public String test(){
        return "test this fucking shit";
    }

    @GetMapping("/GetAllPoint")
    public ResponseEntity<List<Point>> getAllPoint() {
        List<Point> points = userService.getAllPoint();
        return ResponseEntity.ok(points);
    }

    @PostMapping("/SignUp")
    public ResponseEntity<User> signUp(@RequestBody User user){
        User result = userService.createUser(user);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/ChangePassword")
    public ResponseEntity<User> changePassword(@RequestBody User user){
        User result = userService.changePassword(user);
        return ResponseEntity.ok(result);
    }
}
