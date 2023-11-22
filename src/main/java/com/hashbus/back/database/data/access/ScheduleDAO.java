package com.hashbus.back.database.data.access;

import com.hashbus.back.database.mappers.ScheduleMapper;
import com.hashbus.back.model.Schedule;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashSet;

@AllArgsConstructor
@Repository
public class ScheduleDAO {
    private JdbcTemplate jdbcTemplate;
    private ScheduleMapper scheduleMapper;

    public HashSet<Schedule> getSchedulesByJourneyId(long journeyId) {
        return new HashSet<>(jdbcTemplate.query(
                "select * from schedules where journey_ID=?",
                new Object[]{journeyId},
                scheduleMapper
        ));
    }


}
