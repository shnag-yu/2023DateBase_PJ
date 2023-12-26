package com.example.demo.dao;

import com.example.demo.entity.Merchant;
import com.example.demo.rowmapper.MerchantRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class MerchantDao {
    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public MerchantDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 查询自己的商家信息
    public Merchant findById(Long id) {
        String sql = "SELECT * FROM merchant WHERE merchant_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new MerchantRowMapper());
    }

    // 新增商家
    public int save(Merchant merchant) {
        String sql = "INSERT INTO merchant (name, password, addr) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, merchant.getName(), merchant.getPassword(), merchant.getAddr());
    }

    //删除商家
    public int deleteById(Long id) {
//        //先寻找与merchantid一样的product
//        String productsql = "SELECT id FROM product WHERE merchant_id = ?";
//        List<Long> prouduct = jdbcTemplate.query(productsql, new Object[]{id}, (rs, rowNum) -> rs.getLong("id"));
//        for (Long productId : prouduct) {
//            deleteProduct
//        }
        String sql = "DELETE FROM merchant WHERE merchant_id = ?";
        return jdbcTemplate.update(sql, id);
    }

    //通过名字查询商家
    public Merchant findByName(String name) {
        String sql = "SELECT * FROM merchant WHERE name = ?";
        List<Merchant> merchants = jdbcTemplate.query(sql, new Object[]{name}, new MerchantRowMapper());
        if(merchants.isEmpty()) {
            return null;
        } else {
            return merchants.get(0); // 返回匹配的第一个商家
        }
    }

    //查询所有商家
    public List<Merchant> findAll() {
        String sql = "SELECT * FROM merchant"; // 假设表名为 merchant
        return jdbcTemplate.query(sql, new MerchantRowMapper());
    }

    //更新商家信息
    public void updateMerchant(Merchant merchant) {
        String sql = "UPDATE merchant SET name = ?, password = ?, addr = ? WHERE merchant_id = ?";
        jdbcTemplate.update(sql, merchant.getName(), merchant.getPassword(), merchant.getAddr(), merchant.getId());
    }

    public List<Long> findProductIdsByMerchantId(Long merchantId) {
        String sql = "SELECT product_id FROM product WHERE merchant_id = ?";
        return jdbcTemplate.query(sql, new Object[]{merchantId}, (rs, rowNum) -> rs.getLong("product_id"));
    }
}
