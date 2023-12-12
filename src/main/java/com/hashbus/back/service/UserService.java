package com.hashbus.back.service;

import com.hashbus.back.database.data.access.*;
import com.hashbus.back.exceptions.UserException;
import com.hashbus.back.model.Journey;
import com.hashbus.back.model.Point;
import com.hashbus.back.model.SearchDataSchedule;
import com.hashbus.back.model.User;
import com.hashbus.back.exceptions.LoginException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@AllArgsConstructor
public class UserService {
    private UserDAO userDAO;
    private JourneyDAO journeyDAO;
    private ScheduleDAO scheduleDAO;
    private TicketDAO ticketDAO;
    private PointDAO pointDAO;

    public User login(User user) throws LoginException {
        System.out.println(user);
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

    public User createUser(User user) {
        if (userDAO.getUserByUsername(user.getUsername()) != null || userDAO.getUserByEmail(user.getEmail()) != null)
            throw new UserException("user name or email is already used");
        if (userDAO.insertUser(user))
            return user;
        else {
            return null;
        }
    }

    public User changePassword(User user) {
        User result = userDAO.getUserByEmail(user.getEmail());
        if (result == null) {
            throw new UserException("User Not Found!!");
        }
        result.setPassword(user.getPassword());
        if (userDAO.update(result) == 1) return result;
        return null;
    }

    public Set<Journey> getJourneysByPointId(Integer id) {
        Set<Integer> journeysId = pointDAO.getAllJourneysByStopPointId(id);
        HashSet<Journey> journeys = new HashSet<>();
        journeysId.forEach(x ->
                journeys.add(journeyDAO.getJourney(x))
        );
        journeys.addAll(journeyDAO.getJourneysBySourcePointId(id));
        journeys.addAll(journeyDAO.getJourneysByDestinationPointId(id));
        return journeys;
    }

    public List<SearchDataSchedule> getSearchData(Integer startPointId, Integer endPointId, String time) {
        if (startPointId == null || endPointId == null || time == null){
            throw new UserException("Wrong Data!!");
        }
        List<SearchDataSchedule> list = scheduleDAO.getScheduleByPointIdsAndTime(startPointId, endPointId, time);
        return list;
    }
}