package com.example.demo.dao;
import com.example.demo.entity.PriceHistory;

import com.example.demo.rowmapper.PriceHistoryRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Product;


import java.util.List;
/**
 * @Description：
 * @Author ShangYu
 * @date 2023-12-09
 **/
@Repository
public class PriceHistoryDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PriceHistoryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void savePriceHistory(PriceHistory priceHistory) {
        String sql = "INSERT INTO price_history (product_id, price, date) " +
                "VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, priceHistory.getProductId(), priceHistory.getPrice(), priceHistory.getDate());
    }

    public List<PriceHistory> getAllPriceHistorys() {
        String sql = "SELECT * FROM price_history";
        return jdbcTemplate.query(sql, new PriceHistoryRowMapper());
    }


    public List<PriceHistory> getPriceHistorysByProductId(Long productId, String timespan) {
        int days = timespan.equals("week") ? 7 : timespan.equals("month") ? 30 : 365;
//        System.out.println(days);
        String sql = "SELECT * FROM price_history WHERE product_id = ? AND date > DATE_SUB(NOW(), INTERVAL ? DAY)";
        return jdbcTemplate.query(sql, new Object[]{productId, days}, new PriceHistoryRowMapper());
    }

    public void deletePriceHistory(Long priceHistoryId) {
        String sql = "DELETE FROM price_history WHERE price_history_id = ?";
        jdbcTemplate.update(sql, priceHistoryId);
    }

    public Double getLowestPrice(Long productId, String timespan) {
        int days = timespan.equals("week") ? 7 : timespan.equals("month") ? 30 : 365;
        String sql = "SELECT MIN(price) FROM price_history WHERE product_id = ? AND date > DATE_SUB(NOW(), INTERVAL ? DAY)";
        return jdbcTemplate.queryForObject(sql, new Object[]{productId, days}, Double.class);
    }
}
