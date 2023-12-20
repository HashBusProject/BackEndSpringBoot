package com.hashbus.back.database.data.access;

import com.hashbus.back.database.mappers.ScheduleMapper;
import com.hashbus.back.model.Bus;
import com.hashbus.back.model.Journey;
import com.hashbus.back.model.Schedule;
import com.hashbus.back.model.SearchDataSchedule;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Repository
public class ScheduleDAO {
    private JdbcTemplate jdbcTemplate;
    private ScheduleMapper scheduleMapper;
    private PointDAO pointDAO;
    private UserDAO userDAO;
    private JourneyDAO journeyDAO;

    public HashSet<Schedule> getSchedulesByJourneyId(long journeyId) {
        return new HashSet<>(jdbcTemplate.query(
                "select * from schedules where journey_ID = ?",
                new Object[]{journeyId},
                scheduleMapper
        ));
    }

    public List<SearchDataSchedule> getScheduleByPointIdsAndTime(Integer startPointId, Integer endPointId, String time) {
        try {
            return jdbcTemplate.query("""
                            SELECT *
                                    FROM schedules s
                                             INNER JOIN journeys j ON s.journey_ID = j.journey_ID
                                             INNER JOIN buses b ON s.bus_ID = b.bus_ID
                                    WHERE s.time >= ? and b.working = 1
                                      AND (
                                            (j.source_point_ID = ?
                                                OR EXISTS (SELECT *\s
                                                           FROM stop_points_for_journey spj
                                                           WHERE spj.journey_ID = j.journey_ID
                                                             AND spj.point_ID = ?
                                                             and s.next_point_index <= spj.index))
                                            AND (j.destination_point_ID = ?
                                            OR EXISTS (SELECT *
                                                       FROM stop_points_for_journey spj1
                                                       WHERE spj1.journey_ID = j.journey_ID
                                                         AND spj1.point_ID = ?))
                                        )
                                    ORDER BY s.time , next_point_index;""",
                    new Object[]{time, startPointId, startPointId, endPointId, endPointId},
                    rs -> {
                        List<SearchDataSchedule> list = new ArrayList<>();
                        while (rs.next()) {
                            Schedule schedule = new Schedule();
                            Journey journey = new Journey();
                            Bus bus = new Bus();
                            schedule.setBus(rs.getInt("s.bus_ID"));
                            schedule.setJourney(rs.getInt("s.journey_ID"));
                            schedule.setTime(rs.getTime("time"));
                            schedule.setNextPoint(
                                    rs.getInt("next_point_index")
                            );
                            schedule.setPassengersNumber(rs.getInt("passengers_number"));
                            journey.setId(rs.getInt("j.journey_ID"));
                            journey.setSourcePoint(rs.getInt("source_point_ID"));
                            journey.setDestinationPoint(rs.getInt("destination_point_ID"));
                            journey.setName(rs.getString("journey_name"));
                            journey.setPrice(rs.getDouble("ticket_price"));
                            bus.setId(rs.getInt("b.bus_ID"));
                            bus.setDriver(
                                    userDAO.getUserById(rs.getInt("driver_ID"))
                            );
                            bus.setIsWorking(rs.getInt("working") == 1);
                            bus.setX(rs.getDouble("x_point"));
                            bus.setY(rs.getDouble("y_point"));
                            bus.setCap(rs.getInt("capacity"));
                            list.add(new SearchDataSchedule(journey, schedule, bus));
                        }
                        return list;
                    }
            );
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Error " + e.getMessage());
            return new ArrayList<>();
        }
    }


}
