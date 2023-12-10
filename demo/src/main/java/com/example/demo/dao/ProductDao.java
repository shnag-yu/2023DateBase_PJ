package com.example.demo.dao;

import com.example.demo.entity.Product;
import com.example.demo.rowmapper.ProductRowMapper;
import com.example.demo.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Product;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveProduct(Product product) {
        String sql = "INSERT INTO product (name, category, prod_region, prod_date, price, merchant_id, platform_id, prod_desc, last_modify_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW())";
        jdbcTemplate.update(sql, product.getName(), product.getCategory(), product.getProdRegion(),
                product.getProdDate(), product.getPrice(), product.getMerchantId(),
                product.getPlatformId(), product.getDescription());
    }

    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, new ProductRowMapper());
    }

    public Product getProductById(Long productId) {
        String sql = "SELECT p.*, pl.name as platform_name, m.name as merchant_name " +
                "FROM product p " +
                "LEFT JOIN platform pl ON p.platform_id = pl.platform_id " +
                "LEFT JOIN merchant m ON p.merchant_id = m.merchant_id " +
                "WHERE p.product_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{productId}, new ProductRowMapper());
    }

    public List<Product> getProductsByCategory(String category) {
        String sql = "SELECT * FROM product WHERE category = ?";
        return jdbcTemplate.query(sql, new Object[]{category}, new ProductRowMapper());
    }

    public void updateProduct(Product product) {
        String sql = "UPDATE product SET name = ?, category = ?, prod_region = ?, " +
                "prod_date = ?, price = ?, merchant_id = ?, platform_id = ? , prod_desc= ?, last_modify_date=NOW()" +
                "WHERE product_id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getCategory(), product.getProdRegion(),
                product.getProdDate(), product.getPrice(), product.getMerchantId(),
                product.getPlatformId(), product.getDescription(),product.getProductId());
    }

    @Transactional
    public Result deleteProduct(Long productId) {
        try{
            // 首先，从 price_history 表中删除记录
            String deletePriceHistorySql = "DELETE FROM price_history WHERE product_id = ?";
            jdbcTemplate.update(deletePriceHistorySql, productId);

            // 然后，从 favorite 表中删除记录
            String deleteFavoriteSql = "DELETE FROM favorite WHERE product_id = ?";
            jdbcTemplate.update(deleteFavoriteSql, productId);

            // 最后，删除产品本身
            String deleteProductSql = "DELETE FROM product WHERE product_id = ?";
            jdbcTemplate.update(deleteProductSql, productId);
        }
        catch (Exception e){
            return Result.error(400, "删除失败");
        }
        return Result.success();
    }

    // 在 ProductDao 中实现
//    public List<Product> searchProductsByKeyword(String keyword) {
//        String sql = "SELECT * FROM product WHERE name LIKE ? OR prod_desc LIKE ?";
//        String searchTerm = "%" + keyword + "%";
//        return jdbcTemplate.query(sql, new Object[]{searchTerm, searchTerm}, new ProductRowMapper());
//    }
    public List<Product> searchProductsByKeyword(String keyword) {
        String sql = "SELECT p.*, pl.name as platform_name, m.name as merchant_name " +
                "FROM product p " +
                "LEFT JOIN platform pl ON p.platform_id = pl.platform_id " +
                "LEFT JOIN merchant m ON p.merchant_id = m.merchant_id " +
                "WHERE p.name LIKE ? OR p.prod_desc LIKE ?";
        String searchTerm = "%" + keyword + "%";
        return jdbcTemplate.query(sql, new Object[]{searchTerm, searchTerm}, new ProductRowMapper());
    }


    public List<Product> getProductsByMerchantId(Long merchantId) {
//        String sql = "SELECT * FROM product WHERE merchant_id = ?";
        String sql = "SELECT p.*, pl.name as platform_name, m.name as merchant_name " +
                "FROM product p " +
                "LEFT JOIN platform pl ON p.platform_id = pl.platform_id " +
                "LEFT JOIN merchant m ON p.merchant_id = m.merchant_id " +
                "WHERE p.merchant_id = ?";
        return jdbcTemplate.query(sql, new Object[]{merchantId}, new ProductRowMapper());
    }
}
