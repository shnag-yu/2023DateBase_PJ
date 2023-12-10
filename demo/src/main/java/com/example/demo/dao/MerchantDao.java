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
        String sql = "SELECT * FROM merchant WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new MerchantRowMapper());
    }

    // 新增商家
    public int save(Merchant merchant) {
        String sql = "INSERT INTO merchant (name, password, addr) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, merchant.getName(), merchant.getPassword(), merchant.getAddr());
    }

    //删除商家
    public int deleteById(Long id) {
        String sql = "DELETE FROM merchant WHERE ID = ?";
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
}
