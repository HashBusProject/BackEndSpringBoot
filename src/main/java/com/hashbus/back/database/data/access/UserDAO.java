package com.hashbus.back.database.data.access;

import com.hashbus.back.database.mappers.UserMapper;
import com.hashbus.back.model.User;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
@Component
public class UserDAO {
    private JdbcTemplate jdbcTemplate;
    private UserMapper userMapper;

    public User getUserByEmail(String email) {
        return jdbcTemplate.queryForObject(
                "select * from users where email=?",
                new Object[]{email},
                userMapper
        );
    }
    public User getUserById(long id) {
        return jdbcTemplate.queryForObject(
                "select * from users where id=?",
                new Object[]{id},
                userMapper
        );
    }

    public User getUserByUsername(String username) {
        return jdbcTemplate.queryForObject(
                "select * from users where username=?",
                new Object[]{username},
                userMapper
        );
    }

    public boolean insertUser(User user) {
        return jdbcTemplate.update(
                "insert into users (username, name, email, password, role) values (?,?,?,?)",
                user.getUsername(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        ) > 0;
    }

    public boolean deleteUser(User user){
        return jdbcTemplate.update(
                "delete from users where username=?",
                user.getUsername()
        ) > 0;
    }

}
