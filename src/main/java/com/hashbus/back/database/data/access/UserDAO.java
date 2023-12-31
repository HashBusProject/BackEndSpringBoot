package com.hashbus.back.database.data.access;

import com.hashbus.back.database.mappers.UserMapper;
import com.hashbus.back.exceptions.UserException;
import com.hashbus.back.model.User;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    public UserDetails findUserByUsername(String username) {
        User user = getUserByUsername(username);
        org.springframework.security.core.userdetails.User user1 = null;
        try {
            user1 =
                    new org.springframework.security.core.userdetails.User(
                            username, user.getPassword(),
                            Collections.singleton(new SimpleGrantedAuthority(
                                            user.getRole() == 1 ? "Student" :
                                                    user.getRole() == 2 ? "Driver" :
                                                            user.getRole() == 3 ? "Organizer" :
                                                                    "Admin"
                                    )
                            )
                    );
        } catch (Exception e) {
            throw new UserException("User Not Found!");
        }
        return user1;
    }

    public User getUserById(long id) {
        return jdbcTemplate.queryForObject(
                "select * from users where user_id=?",
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
                "insert into users (username, name, email, password, rule_type_Id) values (?,?,?,?,?)",
                user.getUsername(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole() != null ? user.getRole() : 1
        ) > 0;
    }

    public boolean deleteUserById(int id) {
        try {
            int rowsAffected = jdbcTemplate.update("DELETE FROM users WHERE users.user_id=?", id);
            return rowsAffected > 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public List<User> getUserByRole(int role) {
        return jdbcTemplate.query(
                "select * from users where rule_type_Id=?",
                new Object[]{role},
                userMapper
        );
    }

    public int update(User user) {
        return jdbcTemplate.update(
                "update users set password = ? where username=?;",
                user.getPassword(), user.getUsername());
    }

    public int editUser(User user) {
        return jdbcTemplate.update(
                "update users set email = ? , username = ? , name = ? where user_id=?;",
                user.getPassword(), user.getEmail(), user.getUsername(), user.getName(), user.getUserID());
    }

    public int getNumberOfUserByRole(int role) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM users WHERE rule_type_Id = ?",
                new Object[]{role},
                Integer.class
        );
    }

    public Integer getNumberOfUser() {
        return jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }
}
