package com.hashbus.back.database.mappers;

import com.hashbus.back.database.data.access.BusDAO;
import com.hashbus.back.database.data.access.JourneyDAO;
import com.hashbus.back.model.Schedule;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

@Repository
@AllArgsConstructor
public class ScheduleMapper implements RowMapper<Schedule> {
    private final JourneyDAO journeyDAO;
    private final BusDAO busDAO;

    @Override
    public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
        Schedule schedule = new Schedule();
        schedule.setJourney(
                journeyDAO.getJourney(rs.getInt("journey_id"))
        );
        schedule.setBus(
                busDAO.getBusById(rs.getInt("bus_id"))
        );
        schedule.setTime(
                Time.valueOf(rs.getString("time"))
        );
        return null;
    }
}
