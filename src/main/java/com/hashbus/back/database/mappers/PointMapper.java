package com.hashbus.back.database.mappers;

import com.hashbus.back.database.data.access.PointDAO;
import com.hashbus.back.model.Point;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        point.setId(rs.getInt("point_ID"));
        point.setX(rs.getFloat("x_point"));
        point.setX(rs.getFloat("y_point"));
        point.setJourneys(
                pointDAO.getAllJourneysById(point.getId())
        );
        point.setPointName(rs.getString("point_name"));
        return point;
    }
}
