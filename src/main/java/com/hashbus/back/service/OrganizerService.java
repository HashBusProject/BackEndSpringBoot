package com.hashbus.back.service;

import com.hashbus.back.database.data.access.*;
import com.hashbus.back.exceptions.AdminException;
import com.hashbus.back.exceptions.OrganizerExeption;
import com.hashbus.back.model.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class OrganizerService {
    private JourneyDAO journeyDAO;
    private BusDAO busDAO;
    private UserDAO userDAO;
    private ScheduleDAO scheduleDAO;
    private TicketDAO ticketDAO;
    private PointDAO pointDAO;

    public User login(User user) {
        User user1 = userDAO.getUserByUsername(user.getUsername());
        if (user1 == null) {
            throw new OrganizerExeption("Username name not exist!");
        } else {
            if (user.getPassword().equals(user1.getPassword())) {
                if (user1.getRole() == 3) {
                    return user1;
                } else {
                    throw new OrganizerExeption("You are not organizer");
                }
            } else {
                throw new OrganizerExeption("Wrong password!");
            }
        }
    }
    public Journey addJourney(Journey journey) {
        if (journey == null) {
            throw new OrganizerExeption("please insert validate data!!");
        }
        if (journeyDAO.insertJourney(journey))
            return journey;
        return null;
    }

    public Journey editJourney(Journey journey) {
        if (journey == null) {
            throw new OrganizerExeption("please insert validate data!!");
        }
        if (journeyDAO.update(journey))
            return journey;
        return null;
    }

    public List<Journey> getAllJourneys() {
        List<Journey> journeys = journeyDAO.getAllJourneys();
        return journeys;
    }

    public boolean deleteJourney(Journey journey) {
        Journey journey1 = journeyDAO.getJourneyById(journey.getId());
        if (journey1 == null)
            throw new OrganizerExeption("this journey does not exist");
        return journeyDAO.deleteJourney(journey);
    }

    public boolean addPoint(Point point) {
        return pointDAO.insertPoint(point);
    }

    public List<Point> viewAllPoint() {
        return pointDAO.getAllPoint();
    }

    public Integer getNumberOfJourneys() {
        return journeyDAO.getNumberOfJourneys();
    }

    public Integer getNumberOfTickets() {
        return ticketDAO.getNumberOfTickets();
    }

    public List<Ticket> getAllTickets() {
        return ticketDAO.getAllTickets();
    }

    public Boolean addStopPointToJourney(Integer pointId, Integer journeyId, Integer index) {
        return journeyDAO.addStopPointToJourney(pointId, journeyId, index);
    }

    public String getNameOfPoint(Integer pointId) {
        Point point = pointDAO.getPointById(pointId);
        return point.getPointName();
    }

    public Boolean deleteAllTickets() {
        return ticketDAO.deleteAllTickets();
    }

    public List<Schedule> getAllSchedule() {
        return scheduleDAO.getAllSchedule();
    }

    public Integer getNumberOfSchedules() {
        return scheduleDAO.getNumberOfSchedules();
    }

    public String getNameOfJourneyById(Integer journeyId) {
        Journey journey = journeyDAO.getJourneyById(journeyId);
        return journey.getName();
    }

    public Boolean deleteSchedule(Integer scheduleId) {
        return scheduleDAO.deleteSchedule(scheduleId);
    }

    public List<Integer> getIdOfBuses() {
        return busDAO.getIdOfBuses();
    }

    public Boolean addSchedule(Schedule schedule) {
        return scheduleDAO.insertSchedule(schedule);
    }

    public Boolean editSchedule(Schedule schedule) {
        return scheduleDAO.editSchedule(schedule);
    }

    public Point addStopPoint(Point point) {
        if (pointDAO.insertPoint(point))
            return point;
        else {
            return null;
        }
    }


}
