package com.example.demo.dao;

import com.example.demo.entity.Favorite;
import com.example.demo.rowmapper.FavoriteRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FavoriteDao {
    private final JdbcTemplate jdbcTemplate;

    // 构造函数注入JdbcTemplate
    public FavoriteDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 插入一个新的favorite记录
    public void saveFavorite(Favorite favorite) {
        String sql = "INSERT INTO favorite (user_id, product_id, price_lb) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, favorite.getUser_id(), favorite.getProduct_id(), favorite.getPrice_LB());
    }

    // 更新favorite的价格下限
    public void updateFavoritePriceLB(Favorite favorite) {
        String sql = "UPDATE favorite SET price_lb = ? WHERE user_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, favorite.getPrice_LB(), favorite.getUser_id(), favorite.getProduct_id());
    }

    // 删除一条favorite记录
    public void deleteFavorite(Long userId, Long productId) {
        String sql = "DELETE FROM favorite WHERE user_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, userId, productId);
    }

    // 通过用户 ID 和商品 ID 查询 Favorite 记录
    public Favorite getFavoriteByUserIdAndProductId(Long userId, Long productId) {
        String sql = "SELECT * FROM favorite WHERE user_id = ? AND product_id = ?";
        List<Favorite> favorites = jdbcTemplate.query(sql, new Object[]{userId, productId}, new FavoriteRowMapper());
        if (!favorites.isEmpty()) {
            return favorites.get(0);
        }
        return null; // 或者抛出一个自定义的异常
    }
}
