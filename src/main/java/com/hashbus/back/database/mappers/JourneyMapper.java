package com.hashbus.back.database.mappers;

import com.hashbus.back.database.data.access.JourneyDAO;
import com.hashbus.back.database.data.access.ScheduleDAO;
import com.hashbus.back.model.Journey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class JourneyMapper implements RowMapper<Journey> {
    private final ScheduleDAO scheduleDAO;
    private final JourneyDAO journeyDAO;

    @Autowired
    public JourneyMapper(@Lazy ScheduleDAO scheduleDAO, @Lazy JourneyDAO journeyDAO) {
        this.scheduleDAO = scheduleDAO;
        this.journeyDAO = journeyDAO;
    }

    @Override
    public Journey mapRow(ResultSet rs, int rowNum) throws SQLException {
        Journey journey = new Journey();
        journey.setId(rs.getLong("journey_ID"));
        journey.setSourcePoint(
                journeyDAO.getSourcePointForJourneyById(journey.getId())
        );
        journey.setDestinationPoint(
                journeyDAO.getDestinationPointForJourneyById(journey.getId())
        );
        journey.setStopPoints(
                journeyDAO.getStopPointsForJourneyById(journey.getId())
        );
        journey.setJourneys(
                scheduleDAO.getSchedulesByJourneyId(journey.getId())
        );
        return journey;
    }
}
