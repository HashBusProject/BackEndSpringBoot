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
import java.util.Set;

@AllArgsConstructor
@Repository
public class PointDAO {
    private JdbcTemplate jdbcTemplate;
    private JourneyMapper journeyMapper;
    private PointMapper pointMapper;

    public Point getPointById(Integer id) {
        return jdbcTemplate.queryForObject("select * from points where point_ID = ?", new Object[]{id}, pointMapper);
    }

    public boolean insertPoint(Point point) {
        return jdbcTemplate.update("insert into points (x_point, y_point, point_name) values ( ?, ?, ?)",
                point.getX(),
                point.getY(),
                point.getPointName()
        ) > 0;
    }

    public List<Point> getAllPoint() {
        return jdbcTemplate.query("select * from points ", pointMapper);

    }

    public boolean deletePoint(int id) {
        return jdbcTemplate.update("delete from points where point_ID =  ? ",
                id
        ) > 0;
    }
    public Integer getNumberOfPoint(){
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM points ",
                Integer.class
        );
    }


    public Set<Integer> getAllJourneysById(Integer journeyId) {
        return new HashSet<>(
                jdbcTemplate.query(
                        "SELECT stop_points_for_journey.journey_ID\n" +
                                "FROM stop_points_for_journey\n" +
                                "WHERE stop_points_for_journey.point_ID=?\n" +
                                "UNION\n" +
                                "SELECT journeys.journey_ID\n" +
                                "FROM journeys\n" +
                                "WHERE journeys.source_point_ID=?;"
                        , new Object[]{journeyId, journeyId},
                        ((rs, rowNum) -> rs.getInt(1))
                )
        );
    }
}
