package com.example.demo.rowmapper;

import com.example.demo.entity.Platform;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
public class PlatformRowMapper implements RowMapper<Platform>{
    @Override
    public Platform mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Platform platform = new Platform();
        platform.setId(resultSet.getLong("platform_id"));
        platform.setName(resultSet.getString("name"));
        return platform;
    }
}
