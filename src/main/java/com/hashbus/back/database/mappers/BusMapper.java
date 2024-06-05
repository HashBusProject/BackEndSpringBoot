package com.hashbus.back.database.mappers;

import com.hashbus.back.database.data.access.UserDAO;
import com.hashbus.back.model.Bus;

import com.hashbus.back.model.User;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@AllArgsConstructor
public class BusMapper implements RowMapper<Bus> {
    private final UserDAO userDAO;

    @Override
    public Bus mapRow(ResultSet rs, int rowNum) throws SQLException {
        Bus bus = new Bus();
        bus.setId(rs.getInt("bus_ID"));
        bus.setIsWorking(rs.getBoolean("working"));
        bus.setX(rs.getDouble("x_point"));
        bus.setY(rs.getDouble("y_point"));
        User driver = userDAO.getUserById(rs.getInt("Driver_id"));
        bus.setDriver(driver);
        return bus;
    }
}
