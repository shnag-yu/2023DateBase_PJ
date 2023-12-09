package com.example.demo.dao;

import com.example.demo.rowmapper.UserRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.User;
import java.util.List;

@Repository
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    // 构造函数注入JdbcTemplate
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 将新用户保存到数据库
    public void saveUser(User user) {
        String sql = "INSERT INTO user (name, password, age, gender, tel) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getPassword(), user.getAge(), user.getGender(), user.getTel());
    }

    // 从数据库中根据ID获取用户
    public User getUserById(Long userId) {
        String sql = "SELECT * FROM user WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId}, new UserRowMapper());
    }

    // 从数据库中获取所有用户
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    // 更新数据库中现有用户信息
    public void updateUser(User user) {
        String sql = "UPDATE user SET name = ?, password = ?, age = ?, gender = ?, tel = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getName(), user.getPassword(), user.getAge(), user.getGender(), user.getTel(), user.getId());
    }

    // 根据ID从数据库中删除用户
    public void deleteUser(Long userId) {
        String sql = "DELETE FROM user WHERE id = ?";
        jdbcTemplate.update(sql, userId);
    }
}
