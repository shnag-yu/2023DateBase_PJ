package com.example.demo.dao;

import com.example.demo.entity.Product;
import com.example.demo.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Product;


import java.util.List;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveProduct(Product product) {
        String sql = "INSERT INTO product (name, category, prod_region, prod_date, price, merchant_id, platform_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getCategory(), product.getProdRegion(),
                product.getProdDate(), product.getPrice(), product.getMerchantId(),
                product.getPlatformId());
    }

    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, new ProductRowMapper());
    }

    public Product getProductById(Long productId) {
        String sql = "SELECT * FROM product WHERE product_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{productId}, new ProductRowMapper());
    }

    public List<Product> getProductsByCategory(String category) {
        String sql = "SELECT * FROM product WHERE category = ?";
        return jdbcTemplate.query(sql, new Object[]{category}, new ProductRowMapper());
    }

    public void updateProduct(Product product) {
        String sql = "UPDATE product SET name = ?, category = ?, prod_region = ?, " +
                "prod_date = ?, price = ?, merchant_id = ?, platform_id = ? " +
                "WHERE product_id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getCategory(), product.getProdRegion(),
                product.getProdDate(), product.getPrice(), product.getMerchantId(),
                product.getPlatformId(), product.getProductId());
    }

    public void deleteProduct(Long productId) {
        String sql = "DELETE FROM product WHERE product_id = ?";
        jdbcTemplate.update(sql, productId);
    }
}
