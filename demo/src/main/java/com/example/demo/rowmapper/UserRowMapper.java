package com.example.demo.rowmapper;

import com.example.demo.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setName(resultSet.getString("name"));
        user.setPassword(resultSet.getString("password"));
        user.setAge(resultSet.getShort("age"));
        user.setGender(resultSet.getString("gender"));
        user.setTel(resultSet.getLong("tel"));
        return user;
    }
}
