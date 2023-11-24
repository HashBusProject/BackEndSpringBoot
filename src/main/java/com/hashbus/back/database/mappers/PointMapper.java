package com.hashbus.back.database.mappers;

import com.hashbus.back.database.data.access.PointDAO;
import com.hashbus.back.model.Journey;
import com.hashbus.back.model.Point;
import lombok.AllArgsConstructor;
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
    private int count = 0;

    @Autowired
    public PointMapper(@Lazy PointDAO pointDAO) {
        this.pointDAO = pointDAO;
    }

    @Override
    public Point mapRow(ResultSet rs, int rowNum) throws SQLException {
        Point point = new Point();
        ResultSetMetaData rsmd = rs.getMetaData();
//        System.out.println(rsmd.getColumnName(1));
//        System.out.println(rsmd.getColumnName(2));
//        System.out.println(rsmd.getColumnName(3));
//        System.out.println(rsmd.getColumnName(4));
        point.setId(rs.getInt("point_ID"));
        point.setX(rs.getDouble("x_point"));
        point.setY(rs.getDouble("y_point"));
        if (count++ > 0) {

        } else
            point.setJourneys(
                    (HashSet<Journey>) pointDAO.getAllJourneysById(point.getId())
            );
        point.setPointName(rs.getString("point_name"));
        return point;
    }
}
