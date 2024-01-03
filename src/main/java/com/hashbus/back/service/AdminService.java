package com.hashbus.back.service;

import com.hashbus.back.database.data.access.BusDAO;
import com.hashbus.back.database.data.access.PointDAO;
import com.hashbus.back.database.data.access.UserDAO;
import com.hashbus.back.database.mappers.UserMapper;
import com.hashbus.back.exceptions.AdminException;
import com.hashbus.back.model.Bus;
import com.hashbus.back.model.Point;
import com.hashbus.back.model.User;
import com.sun.tools.jconsole.JConsoleContext;
import jnr.ffi.annotations.In;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {
    private UserDAO userDAO;
    private PointDAO pointDAO;
    private BusDAO busDAO;

    public User addUser(User user) {
        User user1 = userDAO.getUserByUsername(user.getUsername());
        if (user1 != null) {
            throw new AdminException("This username already exist");
        }
        user1 = userDAO.getUserByEmail(user.getEmail());
        if (user1 != null) {
            throw new AdminException("This email already exist");
        }
        user.setPassword(Encryption.encrypt(user.getPassword()));
        if (userDAO.insertUser(user))
            return user;
        else {
            return null;
        }

    }

    public int deleteUser(int id) {
        if (userDAO.deleteUserById(id)) {
            return id;
        }else{
            throw new AdminException("this user does not exists");
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
    public Integer deleteBusDriver(int id) {
        if( userDAO.deleteUserById(id) )
            return id ;
        else {
            return null ;
        }
    }
    public List< User > getUser(int role)  {
        if(role > 4 || role < 1)
            throw new AdminException("Please enter valid role");
        return userDAO.getUserByRole(role);
    }
    public Integer getNumberOfUserByRole(int role) {
        if(role > 4 || role < 1)
            throw new AdminException("Please enter valid role");
        return userDAO.getNumberOfUserByRole(role) ;
    }
    public Integer editUser(User user) {
        if(user == null ) {
            throw new AdminException("please enter valid data!!");
        }else {
            return userDAO.editUser(user);
        }
    }
    public List<Point> getAllPoint(){
        return pointDAO.getAllPoint();
    }
    public Boolean deletePoint(int id) {
        if(pointDAO.deletePoint(id)){
            return true ;
        }else{
            return false;
        }
    }
    public Integer getNumberOfPoint(){
        return pointDAO.getNumberOfPoint();
    }
    public List<Bus> getAllBuses(){
        return busDAO.getAllBuses();
    }
    public Integer getNumberOfBuses() {
        return busDAO.getNumberOfBuses() ;
    }
    public Boolean deleteBus( int id ) {
        if( busDAO.deleteBus(id))
            return true ;
        else{
            return false ;
        }
    }
    public Boolean editBus(Bus bus) {
        return busDAO.updateBus(bus) ;
    }
    public Integer getNumberOfUser(){
        return userDAO.getNumberOfUser() ;
    }
    public User login(User user) {
        if (user == null) {
            throw new AdminException("Please enter valid user name!!");
        }
        User user1 = userDAO.getUserByUsername(user.getUsername());
        if (user1 == null) {
            throw new AdminException("User name not exist!");
        }
        if (user.getPassword().equals(user1.getPassword())) {
            if (user1.getRole() == 4) {
                return user1;
            }else{
                throw new AdminException("You are not admin!");
            }
        } else {
            throw new AdminException("Wrong password!");
        }
    }
}
