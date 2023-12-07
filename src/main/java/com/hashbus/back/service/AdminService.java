package com.hashbus.back.service;

import com.hashbus.back.database.data.access.BusDAO;
import com.hashbus.back.database.data.access.PointDAO;
import com.hashbus.back.database.data.access.UserDAO;
import com.hashbus.back.database.mappers.UserMapper;
import com.hashbus.back.model.Bus;
import com.hashbus.back.model.Point;
import com.hashbus.back.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService {
    UserDAO userDAO;
    PointDAO pointDAO;
    BusDAO busDAO;

    public User addUser(User user) {
        if (userDAO.insertUser(user))
            return user;
        else {
            return null;
        }
    }

    public User deleteUser(User user) {
        if (userDAO.deleteUser(user)) {
            return user;
        } else {
            return null;
        }
    }

    public Point addStopPoint(Point point) {
        if (pointDAO.insertPoint(point))
            return point;
        else {
            return null;
        }
    }

    public Bus addBus(Bus bus) {
        if (busDAO.insertBus(bus))
            return bus;
        else {
            return null;
        }
    }
    public User deleteBusDriver(User user) {
        if( userDAO.deleteUser(user) )
            return user ;
        else {
            return null ;
        }

    }
}
