package com.example.demo.rowmapper;

import com.example.demo.entity.PriceHistory;
import org.springframework.jdbc.core.RowMapper;

/**
 * @Description：
 * @Author ShangYu
 * @date 2023-12-09
 **/
public class PriceHistoryRowMapper implements RowMapper<PriceHistory> {
    @Override
    public PriceHistory mapRow(java.sql.ResultSet resultSet, int rowNum) throws java.sql.SQLException {
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setProductId(resultSet.getLong("product_id"));
        priceHistory.setPrice(resultSet.getDouble("price"));
        priceHistory.setDate(resultSet.getDate("date"));
        return priceHistory;
    }
}

