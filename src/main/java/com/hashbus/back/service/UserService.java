package com.hashbus.back.service;

import com.hashbus.back.database.data.access.*;
import com.hashbus.back.exceptions.UserException;
import com.hashbus.back.model.*;
import com.hashbus.back.exceptions.LoginException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
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
    private BusDAO busDAO;

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
                journeys.add(journeyDAO.getJourneyById(x))
        );
        journeys.addAll(journeyDAO.getJourneysBySourcePointId(id));
        journeys.addAll(journeyDAO.getJourneysByDestinationPointId(id));
        return journeys;
    }

    public List<SearchDataSchedule> getSearchData(Integer startPointId, Integer endPointId, String time) {
        if (startPointId == null || endPointId == null || time == null) {
            throw new UserException("Wrong Data!!");
        }
        List<SearchDataSchedule> list = scheduleDAO.getScheduleByPointIdsAndTime(startPointId, endPointId, time);
        return list;
    }

    public List<Journey> getAllJournys() {
        List<Journey> journeys = journeyDAO.getAllJourneys();
        if (journeys.size() == 0) {
            throw new UserException("There is no journys");
        }
        return journeys;
    }

    public Point getPointById(Integer id) {
        if (id == null) {
            throw new UserException("Wrong Data!");
        }
        Point point = pointDAO.getPointById(id);
        return point;
    }

    public Journey getJourneyById(Integer id) {
        if (id == null) {
            throw new UserException("Wrong Data!");
        }
        Journey journey = journeyDAO.getJourneyById(id);
        return journey;
    }

    public Bus getBusById(Integer id) {
        if (id == null) {
            throw new UserException("Wrong Data!");
        }
        return busDAO.getBusById(id);
    }

    public List<Ticket> getTicketsByUserId(Integer id) {
        if (id == null) {
            throw new UserException("Wrong Data!");
        }
        List<Ticket> tickets = ticketDAO.getTicketsByUserId(id);
        return tickets;
    }

    public Boolean buyTicket(Integer userId, Integer journeyId) {
        if (userId == null || journeyId == null) {
            throw new UserException("Wrong Data!");
        }
        return ticketDAO.buyTicket(userId, journeyId);
    }

    public List<Point> getAllPointByJourneyId(Integer journeyId) {
        List<Point> points = new ArrayList<>();
        points.add(pointDAO.getPointById(journeyDAO.getSourcePointForJourneyById(journeyId)));
        for (Integer pointId : journeyDAO.getStopPointsForJourneyById(journeyId)) {
            points.add(pointDAO.getPointById(pointId));
        }
        points.add(pointDAO.getPointById(journeyDAO.getDestinationPointForJourneyById(journeyId)));
        return points;
    }

    public Boolean confirmRide(@NonNull Integer userId, @NonNull Integer journeyId,
                               @NonNull Integer busId, Schedule scheduleId) {
        if (busId.equals(scheduleId.getBus()) && journeyId.equals(scheduleId.getJourney())) {
            return ticketDAO.makeTheTicketUsedByUserIdAndJourneyId(userId, journeyId);
        } else
            return false;
    }

    public Boolean reserveASite(@NonNull Integer scheduleId) {
        Schedule schedule = scheduleDAO.getScheduleById(scheduleId);
        return scheduleDAO.updatePassengerNumber(scheduleId, schedule.getPassengersNumber() + 1);
    }

    public Boolean cancelASite(@NonNull Integer scheduleId) {
        Schedule schedule = scheduleDAO.getScheduleById(scheduleId);
        return scheduleDAO.updatePassengerNumber(scheduleId, schedule.getPassengersNumber() - 1);
    }

    public DriverData loginForDriver(@NonNull User user){
        if(user.getUsername() == null)
            throw new UserException("Wrong username or password");
        User authUser = userDAO.getUserByUsername(user.getUsername());
        if(authUser == null)
            throw new UserException("User Not Found!");
        if(!authUser.getPassword().equals(user.getPassword()))
            throw new UserException("Wrong username or password");
        Bus bus = busDAO.getBusByDriverID(authUser.getUserID());
        authUser.setPassword(null);
        return new DriverData(authUser, bus);
    }
}