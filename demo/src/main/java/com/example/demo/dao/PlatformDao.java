package com.example.demo.dao;
import com.example.demo.entity.Platform;
import com.example.demo.rowmapper.PlatformRowMapper;
import com.example.demo.rowmapper.UserRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.User;
import java.util.List;

/**
 * @Description：
 * @Author ShangYu
 * @date 2023-12-10
 **/
@Repository
public class PlatformDao {
    private final JdbcTemplate jdbcTemplate;
    public PlatformDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Platform> getAllPlatforms() {
        String sql = "SELECT * FROM platform";
        return jdbcTemplate.query(sql, new PlatformRowMapper());
    }

    public Platform getPlatformById(Long platformId) {
        String sql = "SELECT * FROM platform WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{platformId}, new PlatformRowMapper());
    }
    public Platform getPlatformByName(String name) {
        String sql = "SELECT * FROM platform WHERE name = ?";
        List<Platform> platforms = jdbcTemplate.query(sql, new Object[]{name}, new PlatformRowMapper());
        if (!platforms.isEmpty()) {
            return platforms.get(0);
        } else {
            // 处理没有匹配用户的情况，可能返回 null 或抛出自定义异常
            return null;
        }
    }
}
