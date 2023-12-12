package com.hashbus.back.database.mappers;

import com.hashbus.back.database.data.access.PointDAO;
import com.hashbus.back.model.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashSet;

@Component
public class PointMapper implements RowMapper<Point> {
    private final PointDAO pointDAO;

    @Autowired
    public PointMapper(@Lazy PointDAO pointDAO) {
        this.pointDAO = pointDAO;
    }

    @Override
    public Point mapRow(ResultSet rs, int rowNum) throws SQLException {
        Point point = new Point();
        ResultSetMetaData rsmd = rs.getMetaData();
        point.setId(rs.getInt("point_ID"));
        point.setX(rs.getDouble("x_point"));
        point.setY(rs.getDouble("y_point"));
        point.setPointName(rs.getString("point_name"));
        point.setJourneysID(
                (HashSet<Integer>) pointDAO.getAllJourneysByStopPointId(point.getId())
        );
        return point;
    }
}
