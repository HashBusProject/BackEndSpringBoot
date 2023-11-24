package com.hashbus.back.database.data.access;

import com.hashbus.back.database.mappers.JourneyMapper;
import com.hashbus.back.database.mappers.PointMapper;
import com.hashbus.back.model.Journey;
import com.hashbus.back.model.Point;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;

@AllArgsConstructor
@Repository
public class JourneyDAO {

    private JdbcTemplate jdbcTemplate;

    private JourneyMapper journeyMapper;

    private PointMapper pointMapper;
    public Journey getJourney(long id) {
        return jdbcTemplate.queryForObject("select * from journeys where journey_ID = ?", new Object[]{id}, journeyMapper);
    }
    public Journey getAllJourney(){
        return jdbcTemplate.queryForObject("select * from journeys )", journeyMapper);
    }
    public boolean insertJourney(Journey journey) {
        return jdbcTemplate.update("insert into journeys (journey_ID ,  source_point_ID , destination_point_ID , journey_name) values ( ? , ?  , ? , ?  )",
                journey.getId(),
                journey.getSourcePoint().getId(),
                journey.getDestinationPoint().getId(),
                journey.getName()
        ) > 0;
    }

    public boolean deleteJourney(Journey journey) {
        return jdbcTemplate.update("delete from journeys where journey_ID = ? ",
                journey.getId()
        ) > 0;
    }

    public Point getSourcePointForJourneyById(Integer journeyId) {
        return jdbcTemplate.queryForObject("select * from points where point_ID = (select journeys.source_point_ID from journeys where journey_ID = ?)", new Object[]{journeyId}, pointMapper);
    }

    public Point getDestinationPointForJourneyById(long journeyId) {
        return jdbcTemplate.queryForObject("select * from points where point_ID = (select journeys.destination_point_ID from journeys where journey_ID = ?)", new Object[]{journeyId}, pointMapper);
    }

    public HashSet<Integer> getStopPointsForJourneyById(Integer journeyId) {
        List<Integer> actors = jdbcTemplate.query(
                // TODO Check this
                "select * from points where point_ID = (select  from stop_points_for_journey where journey_ID = ?)",
                new Object[]{journeyId},
                (rs, num) -> rs.getInt("point_ID")
        );
        return new HashSet<>(actors);
    }
    public boolean update(Journey journey){
        return jdbcTemplate.update("update journeys set journey_name =? , source_point_ID =? , destination_point_ID = ? where journey_ID=?" ,
                journey.getName() ,
                journey.getSourcePoint().getId(),
                journey.getDestinationPoint().getId(),
                journey.getId()
        ) > 0 ;
    }
}
