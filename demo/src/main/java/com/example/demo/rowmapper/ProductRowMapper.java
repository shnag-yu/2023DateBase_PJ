package com.example.demo.rowmapper;

import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * @Description：
 * @Author ShangYu
 * @date 2023-12-09
 **/
public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Product product = new Product();
        product.setProductId(resultSet.getLong("product_id"));
        product.setName(resultSet.getString("name"));
        product.setCategory(resultSet.getString("category"));
        product.setProdRegion(resultSet.getString("prod_region"));
        product.setProdDate(String.valueOf(resultSet.getDate("prod_date")));
        product.setPrice(resultSet.getDouble("price"));
        product.setMerchantId(resultSet.getLong("merchant_id"));
        product.setPlatformId(resultSet.getLong("platform_id"));
        return product;
    }
}
