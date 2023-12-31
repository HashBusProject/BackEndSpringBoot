package com.hashbus.back.control;
import com.hashbus.back.database.data.access.UserDAO;
import com.hashbus.back.jwt.JwtUtils;
import com.hashbus.back.model.User;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@ComponentScan
public class AuthenticationControl {
    private final AuthenticationManager authenticationManager;
    private final UserDAO userDao;
    private final JwtUtils jwtUtils;
    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody User request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Wrong Password or Username try Again!!", HttpStatus.BAD_REQUEST);
        }
        final UserDetails userDetails = userDao.findUserByUsername(request.getUsername());
        if (userDetails != null) {
            String token = jwtUtils.generateToken(userDetails);
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        return new ResponseEntity<>("Wrong Password or Username try Again!!", HttpStatus.BAD_REQUEST);
    }

}