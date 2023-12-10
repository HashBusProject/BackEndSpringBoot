package com.hashbus.back.database.data.access;

import com.hashbus.back.database.mappers.UserMapper;
import com.hashbus.back.model.User;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@AllArgsConstructor
@Repository
@Component
public class UserDAO {
    private JdbcTemplate jdbcTemplate;
    private UserMapper userMapper;

    public User getUserByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject(
                    "select * from users where email=?",
                    new Object[]{email},
                    userMapper
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public User getUserById(long id) {
        return jdbcTemplate.queryForObject(
                "select * from users where id=?",
                new Object[]{id},
                userMapper
        );
    }

    public User getUserByUsername(String username) {
        try {
            return jdbcTemplate.queryForObject(
                    "select * from users where username=?",
                    new Object[]{username},
                    userMapper
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public boolean insertUser(User user) {
        return jdbcTemplate.update(
                "insert into users (username, name, email, password, rule_type_ID) values (?,?,?,?,?)",
                user.getUsername(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole() != null ? user.getRole() : 1
        ) > 0;
    }

    public boolean deleteUser(User user) {
        return jdbcTemplate.update(
                "delete from users where username=?",
                user.getUsername()
        ) > 0;
    }

    public List<User> getUserByRole(int role) {
        return jdbcTemplate.query(
                "select * from users where role=?",
                new Object[]{role},
                userMapper
        );
    }

    public int update(User user) {
        return jdbcTemplate.update(
                "update users set password = ? where username=?;",
                user.getPassword(), user.getUsername());
    }
}
