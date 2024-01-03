package com.hashbus.back.service;

import com.hashbus.back.database.data.access.*;
import com.hashbus.back.exceptions.LoginException;
import com.hashbus.back.exceptions.UserException;
import com.hashbus.back.model.DataSchedule;
import com.hashbus.back.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DriverService {
    private JourneyDAO journeyDAO;
    private ScheduleDAO scheduleDAO;
    private PointDAO pointDAO;
    private BusDAO busDAO;
    private UserDAO userDAO;

    public User login(User user) throws LoginException {
        User user1 = userDAO.getUserByUsername(user.getUsername());
        user.setPassword(Encryption.encrypt(user.getPassword()));
        if (user1 == null) {
            throw new LoginException("Wrong Username!!");
        } else if (!user.getUsername().equals(user1.getUsername()) || !user1.getPassword().equals(user.getPassword())) {
            throw new LoginException("Wrong Login!!");
        } else if (user1.getRole() != 2)
            throw new LoginException("Invalid Role");
        return user1;
    }

    public List<DataSchedule> getSchedulesByBusId(Integer busId) {
        if (busId == null) {
            throw new RuntimeException("");
        }
        return scheduleDAO.getSchedulesDataByBusId(busId);
    }

    public Boolean updateNextPointIndexByScheduleId(Integer scheduleId, Integer previousIndex) {
        return scheduleDAO.updateNextPointIndex(scheduleId, previousIndex);
    }

    public Boolean setScheduleAsFinish(Integer scheduleId) {
        return scheduleDAO.setScheduleFinished(scheduleId);
    }

    public Boolean updateLocation(Integer busId, Double latitude, Double longitude) {
        return busDAO.updateLocation(busId, latitude, longitude);
    }

    public boolean changeEmail(User user) {
        User user1 = userDAO.getUserById(user.getUserID());
        if(user1 != null){
            throw new UserException("This email already exist") ;
        }
        user.setPassword(Encryption.encrypt(user.getPassword()));
        if (!(userDAO.getUserById(user.getUserID()).getPassword().equals(user.getPassword()))) {
            throw new UserException("Email does not match!");
        }
        return userDAO.changeEmail(user) == Boolean.TRUE ;
    }
}
