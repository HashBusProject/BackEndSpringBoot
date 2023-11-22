package com.hashbus.back.service;

import com.hashbus.back.database.data.access.*;
import com.hashbus.back.model.Point;
import com.hashbus.back.model.User;
import com.hashbus.back.exceptions.LoginException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class UserService {
    private UserDAO userDAO;
    private JourneyDAO journeyDAO;
    private ScheduleDAO scheduleDAO;
    private TicketDAO ticketDAO;
    private PointDAO pointDAO;

    public User login(User user) throws LoginException {
        User user1 = userDAO.getUserByUsername(user.getUsername());
        if (user1 == null) {
            throw new LoginException("Wrong Username!!");
        } else if (!user.getUsername().equals(user1.getUsername()) || !user1.getPassword().equals(user.getPassword())) {
            throw new LoginException("Wrong Login!!");
        }
        return user1;
    }
    public List<Point> getAllPoint() {
        return pointDAO.getAllPoint();
    }
}
