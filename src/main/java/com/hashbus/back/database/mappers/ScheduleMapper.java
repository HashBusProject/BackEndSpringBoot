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
    @Override
    public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
        Schedule schedule = new Schedule();
        schedule.setScheduleId(rs.getInt("schedule_id"));
        schedule.setJourney(rs.getInt("journey_id"));
        schedule.setBus(rs.getInt("bus_id"));
        schedule.setTime(
                Time.valueOf(rs.getString("time"))
        );
        schedule.setPassengersNumber(rs.getInt("passengers_number"));
        return schedule;
    }
}
