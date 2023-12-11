package com.hashbus.back.database.mappers;

import com.hashbus.back.model.User;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapper implements RowMapper<User> {
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setUsername(resultSet.getString("username"));
        user.setName(resultSet.getString("name"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setUserID(resultSet.getInt("user_ID"));
        user.setRole(resultSet.getInt("role"));
        return user;
    }
}
