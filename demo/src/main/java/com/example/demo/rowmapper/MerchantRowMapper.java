package com.example.demo.rowmapper;

import com.example.demo.entity.Merchant;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MerchantRowMapper implements RowMapper<Merchant>{

    @Override
    public Merchant mapRow(ResultSet rs, int rowNum) throws SQLException {
        Merchant merchant = new Merchant();
        merchant.setId(rs.getLong("merchant_id"));
        merchant.setName(rs.getString("name"));
        merchant.setPassword(rs.getString("password"));
        merchant.setAddr(rs.getString("addr"));
        return merchant;
    }
}
