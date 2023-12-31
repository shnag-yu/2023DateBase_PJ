package com.example.demo.dao;
import com.example.demo.entity.PriceHistory;

import com.example.demo.rowmapper.PriceHistoryRowMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Product;


import java.util.List;
import java.util.Map;

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

    @Transactional
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

    @Transactional
    public void deletePriceHistory(Long priceHistoryId) {
        String sql = "DELETE FROM price_history WHERE price_history_id = ?";
        jdbcTemplate.update(sql, priceHistoryId);
    }

    public Double getLowestPrice(Long productId, String timespan) {
        int days = timespan.equals("week") ? 7 : timespan.equals("month") ? 30 : 365;
        String sql = "SELECT MIN(price) FROM price_history WHERE product_id = ? AND date > DATE_SUB(NOW(), INTERVAL ? DAY)";
        return jdbcTemplate.queryForObject(sql, new Object[]{productId, days}, Double.class);
    }

    public List<Map<String, Object>> getMaxPriceRanges(String category, String timespan) {
        int days = timespan.equals("week") ? 7 : timespan.equals("month") ? 30 : 365;
        if(category.equals("所有")) category = null;
        String sql =
                "SELECT p.name AS product_name, ROUND((MAX(ph.price) - MIN(ph.price)),2) AS price_range "+
                "FROM product AS p "+
                "JOIN price_history AS ph ON p.product_id = ph.product_id "+
                "WHERE (? IS NULL OR p.category = ?) AND ph.date > DATE_SUB(NOW(), INTERVAL ? DAY) "+
                "GROUP BY p.name "+
                "ORDER BY price_range DESC "+
                "LIMIT 10; ";
        return jdbcTemplate.queryForList(sql, new Object[]{category, category, days});
    }

    public List<Map<String, Object>> getMinPriceRanges(String category, String timespan) {
        int days = timespan.equals("week") ? 7 : timespan.equals("month") ? 30 : 365;
        if(category.equals("所有")) category = null;
        String sql =
                "SELECT p.name AS product_name, ROUND((MAX(ph.price) - MIN(ph.price)),2) AS price_range "+
                "FROM product AS p "+
                "JOIN price_history AS ph ON p.product_id = ph.product_id "+
                "WHERE (? IS NULL OR p.category = ?) AND ph.date > DATE_SUB(NOW(), INTERVAL ? DAY) "+
                "GROUP BY p.name "+
                "ORDER BY price_range ASC "+
                "LIMIT 10; ";
        return jdbcTemplate.queryForList(sql, new Object[]{category, category, days});
    }

    @Transactional
    public void updatePriceHistory(PriceHistory priceHistory) {
        String sql = "UPDATE price_history SET price = ? WHERE product_id = ? AND date = ?";
        jdbcTemplate.update(sql, priceHistory.getPrice(), priceHistory.getProductId(), priceHistory.getDate());
    }
}
