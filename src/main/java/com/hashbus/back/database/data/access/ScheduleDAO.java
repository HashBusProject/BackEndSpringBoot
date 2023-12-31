package com.hashbus.back.database.data.access;

import com.hashbus.back.database.mappers.ScheduleMapper;
import com.hashbus.back.model.*;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.*;

@AllArgsConstructor
@Repository
public class ScheduleDAO {
    private JdbcTemplate jdbcTemplate;
    private ScheduleMapper scheduleMapper;
    private PointDAO pointDAO;
    private UserDAO userDAO;
    private JourneyDAO journeyDAO;

    public List<Schedule> getSchedulesByJourneyId(long journeyId) {
        return (jdbcTemplate.query(
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

    public List<Schedule> getAllSchedule() {
        try {
            return jdbcTemplate.query("select * from schedules", scheduleMapper);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public Integer getNumberOfSchedules() {
        return jdbcTemplate.queryForObject("select count(*) from schedules",
                Integer.class);
    }

    public boolean deleteSchedule(Integer scheduleId) {
        return jdbcTemplate.update("delete from schedules where schedule_id = ?",
                scheduleId) > 0;
    }

    public Boolean insertSchedule(Schedule schedule) {
        return jdbcTemplate.update("insert into schedules (journey_id , bus_id , time  , date) values (? , ? , ?  , ? )",
                schedule.getJourney(),
                schedule.getBus(),
                schedule.getTime().toString(),
                schedule.getDate()
        ) > 0;
    }

    public Boolean editSchedule(Schedule schedule) {
        return jdbcTemplate.update("insert into schedules (journey_id , bus_id , time , date ) values ( ? , ? , ? , ? )",
                schedule.getJourney(),
                schedule.getBus(),
                schedule.getTime().toString(),
                schedule.getDate()
        ) > 0;
    }

    public List<DataSchedule> getSchedulesDataByBusId(Integer busId) {
        try {
            return jdbcTemplate.query("""
                            SELECT * from schedules s, journey j where s.finished=0 and s.bus_ID=? order by s.time asc
                            """,
                    new Object[]{busId},
                    (rs -> {
                        List<DataSchedule> dataSchedules = new ArrayList<>();
                        while (rs.next()) {
                            Journey journey = new Journey();
                            Schedule schedule = new Schedule();
                            schedule.setBus(rs.getInt("bus_ID"));
                            schedule.setJourney(rs.getInt("s.journey_ID"));
                            schedule.setTime(rs.getTime("time"));
                            schedule.setNextPoint(
                                    rs.getInt("next_point_index")
                            );
                            journey.setId(rs.getInt("j.journey_ID"));
                            journey.setSourcePoint(rs.getInt("source_point_ID"));
                            journey.setDestinationPoint(rs.getInt("destination_point_ID"));
                            journey.setName(rs.getString("journey_name"));
                            journey.setPrice(rs.getDouble("ticket_price"));
                            dataSchedules.add(new DataSchedule(journey, schedule));
                        }
                        return dataSchedules;
                    })
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public boolean updateNextPointIndex(Integer scheduleId, Integer previousIndex) {
        try {
            return jdbcTemplate.update("""
                        update schedules set next_point_index=? where schedule_ID=?
                    """, previousIndex, scheduleId + 1) > 1;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public boolean setScheduleFinished(Integer scheduleId) {
        try {
            return jdbcTemplate.update("""
                    UPDATE  scheules SET finished=1 where scheule_ID=?
                    """, scheduleId) > 1;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public List<Map<String, Object>> getSumOfPassengerNumber() {
        return jdbcTemplate.query("SELECT date, SUM(passengers_number) AS total_passengers FROM schedules GROUP BY date", rs -> {
            List<Map<String, Object>> resultList = new ArrayList<>();
            while (rs.next()) {
                String date = rs.getString("date");
                int totalPassengers = rs.getInt("total_passengers");
                Map<String, Object> map = new HashMap<>();
                map.put("date", date);
                map.put("totalPassenger", totalPassengers);

                resultList.add(map);
            }
            return resultList;
        });
    }

    public Map<String, Object> getTheTopJourney() {
        return jdbcTemplate.query("SELECT journey_id, SUM(passengers_number) AS total_passengers " +
                "FROM schedules " +
                "GROUP BY journey_id " +
                "ORDER BY total_passengers DESC " +
                "LIMIT 1", rs -> {
            if (rs.next()) {
                int journeyId = rs.getInt("journey_id");
                String journeyName = journeyDAO.getJourneyById(journeyId).getName();
                int totalPassengers = rs.getInt("total_passengers");
                Map<String, Object> map = new HashMap<>();
                map.put("journeyName", journeyName);
                map.put("totalPassenger", totalPassengers);
                return map;
            } else {
                return new HashMap<>();
            }
        });
    }
}