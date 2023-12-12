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

@AllArgsConstructor
@Repository
public class ScheduleDAO {
    private JdbcTemplate jdbcTemplate;
    private ScheduleMapper scheduleMapper;

    public HashSet<Schedule> getSchedulesByJourneyId(long journeyId) {
        return new HashSet<>(jdbcTemplate.query(
                "select * from schedules where journey_ID = ?",
                new Object[]{journeyId},
                scheduleMapper
        ));
    }

    public List<SearchDataSchedule> getScheduleByPointIdsAndTime(Integer startPointId, Integer endPointId, String time) {
        try {
            return jdbcTemplate.query(
                    "SELECT *\n" +
                            "FROM schedules s\n" +
                            "         INNER JOIN journeys j ON s.journey_ID = j.journey_ID\n" +
                            "         INNER JOIN buses b ON s.bus_ID = b.bus_ID\n" +
                            "WHERE s.time >= ? and b.working = 1\n" +
                            "  AND (\n" +
                            "        (j.source_point_ID = ?\n" +
                            "            OR EXISTS (SELECT *\n" +
                            "                       FROM stop_points_for_journey spj\n" +
                            "                       WHERE spj.journey_ID = j.journey_ID\n" +
                            "                         AND spj.point_ID = ?))\n" +
                            "        AND (j.destination_point_ID = ?\n" +
                            "        OR EXISTS (SELECT *\n" +
                            "                   FROM stop_points_for_journey spj\n" +
                            "                   WHERE spj.journey_ID = j.journey_ID\n" +
                            "                     AND spj.point_ID = ?))\n" +
                            "    )\n" +
                            "ORDER BY time;",
                    new Object[]{time, startPointId, startPointId, endPointId, endPointId},
                    rs -> {
                        List<SearchDataSchedule> list = new ArrayList<>();
                        while (rs.next()) {
                            Schedule schedule = new Schedule();
                            Journey journey = new Journey();
                            Bus bus = new Bus();
                            schedule.setBus(rs.getInt(1));
                            schedule.setJourney(rs.getInt(2));
                            schedule.setTime(rs.getTime(3));
                            journey.setId(rs.getInt(4));
                            journey.setSourcePoint(rs.getInt(5));
                            journey.setDestinationPoint(rs.getInt(6));
                            journey.setName(rs.getString(7));
                            bus.setId(rs.getInt(8));
//                            bus.setDriver(rs.getInt(9));
                            bus.setIsWorking(rs.getInt(10) == 1);
                            bus.setX(rs.getDouble(11));
                            bus.setY(rs.getDouble(12));
                            list.add(new SearchDataSchedule(journey, schedule, bus));
                        }
                        System.out.println(list);
                        return list;
                    }
            );
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Error " + e.getMessage());
            return new ArrayList<>();
        }
    }


}
