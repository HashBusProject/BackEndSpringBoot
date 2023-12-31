package com.hashbus.back.database.data.access;

import com.hashbus.back.database.mappers.JourneyMapper;
import com.hashbus.back.database.mappers.PointMapper;
import com.hashbus.back.model.Journey;
import com.hashbus.back.model.Point;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Repository
public class JourneyDAO {

    private JdbcTemplate jdbcTemplate;

    private JourneyMapper journeyMapper;

    private PointMapper pointMapper;

    public Journey getJourneyById(Integer id) {
        return jdbcTemplate.queryForObject("select * from journeys where journey_ID = ?", new Object[]{id}, journeyMapper);
    }

    public List<Journey> getAllJourneys() {
        return jdbcTemplate.query("select * from journeys ", journeyMapper);
    }

    public boolean insertJourney(Journey journey) {
        return jdbcTemplate.update("insert into journeys (source_point_ID , destination_point_ID , journey_name , ticket_price) values ( ?  , ? , ? ,    ?  )",
                journey.getSourcePoint(),
                journey.getDestinationPoint(),
                journey.getName() ,
                journey.getPrice()

        ) > 0;
    }

    public boolean deleteJourney(Journey journey) {
        return jdbcTemplate.update("delete from journeys where journey_ID = ? ",
                journey.getId()
        ) > 0;
    }

    public Integer getSourcePointForJourneyById(Integer journeyId) {
        return jdbcTemplate.queryForObject("select journeys.source_point_ID from journeys where journey_ID = ?", new Object[]{journeyId}, Integer.class);
    }

    public Integer getDestinationPointForJourneyById(long journeyId) {
        return jdbcTemplate.queryForObject("select destination_point_ID from journeys where journey_ID = ?", new Object[]{journeyId}, Integer.class);
    }

    public Integer getNumberOfJourneys() {
        return jdbcTemplate.queryForObject("select count(*) from journeys",
                Integer.class);
    }

    public List<Integer> getStopPointsForJourneyById(Integer journeyId) {
        List<Integer> actors = new ArrayList<>();
        try {
            actors = jdbcTemplate.query(
                    "SELECT points.*\n" +
                            "FROM points\n" +
                            "INNER JOIN stop_points_for_journey spj ON points.point_ID = spj.point_ID\n" +
                            "WHERE spj.journey_ID = ?\n" +
                            "Order by spj.journey_ID and spj.index",
                    new Object[]{journeyId},
                    (rs, num) -> rs.getInt("point_ID")
            );
            return actors;
        } catch (EmptyResultDataAccessException e) {
            return actors;
        }
    }

    public boolean update(Journey journey) {
        return jdbcTemplate.update("update journeys set journey_name =? , source_point_ID =? , destination_point_ID = ? where journey_ID=?",
                journey.getName(),
                journey.getSourcePoint(),
                journey.getDestinationPoint(),
                journey.getId()
        ) > 0;
    }

    public Set<Journey> getJourneysBySourcePointId(Integer pointId) {
        try {
            return new HashSet<>(
                    jdbcTemplate.query(
                            "SELECT * FROM journeys where source_point_ID = ?",
                            new Object[]{pointId},
                            journeyMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return new HashSet<>();
        }
    }

    public Set<Journey> getJourneysByDestinationPointId(Integer pointId) {
        try {
            return new HashSet<>(
                    jdbcTemplate.query(
                            "SELECT * FROM journeys where destination_point_ID = ?",
                            new Object[]{pointId},
                            journeyMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return new HashSet<>();
        }
    }

    public Boolean addStopPointToJourney(Integer pointId, Integer journeyId, Integer index) {
        try {
            return jdbcTemplate.update(
                    "INSERT INTO stop_points_for_journey (journey_id, point_id, `index`) VALUES (?, ?, ?)",
                    journeyId,
                    pointId,
                    index
            ) > 0;
        } catch (EmptyResultDataAccessException e) {
            return Boolean.FALSE;
        }
    }



}
