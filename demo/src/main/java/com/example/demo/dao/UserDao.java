package com.example.demo.dao;

import com.example.demo.entity.Product;
import com.example.demo.rowmapper.UserRowMapper;
import com.example.demo.rowmapper.ProductRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public User getUserByName(String name) {
        String sql = "SELECT * FROM user WHERE name = ?";
        List<User> users = jdbcTemplate.query(sql, new Object[]{name}, new UserRowMapper());
        if (!users.isEmpty()) {
            return users.get(0);
        } else {
            // 处理没有匹配用户的情况，可能返回 null 或抛出自定义异常
            return null;
        }
    }

    public List<Map<String, Object>> getFavoriteProducts(String gender, int startAge, int endAge) {
        String sql =
                "SELECT p.name AS product_name, COUNT(f.product_id) AS favorite_count " +
                        "FROM favorite f " +
                        "JOIN product p ON f.product_id = p.product_id " +
                        "JOIN user u ON f.user_id = u.ID " +
                        "WHERE u.gender = ? AND u.age BETWEEN ? AND ? " +
                        "GROUP BY p.name " +
                        "ORDER BY favorite_count DESC " +
                        "LIMIT 10";
         return jdbcTemplate.queryForList(sql, new Object[]{gender, startAge, endAge});
    }

    public List<Map<String, Object>> getFavoriteCategoryRatio(String gender, int startAge, int endAge) {
        String sql =
                "SELECT p.category as name, COUNT(*) / (SELECT COUNT(*) FROM favorite f INNER JOIN user u ON f.user_id = u.ID WHERE u.gender = ? AND u.age BETWEEN ? AND ?) AS value " +
                        "FROM favorite f " +
                        "JOIN product p ON f.product_id = p.product_id " +
                        "JOIN user u ON f.user_id = u.ID " +
                        "WHERE u.gender = ? AND u.age BETWEEN ? AND ? " +
                        "GROUP BY p.category "+
                        "ORDER BY value DESC " ;

        return jdbcTemplate.queryForList(sql, gender, startAge, endAge, gender, startAge, endAge);
    }
}
