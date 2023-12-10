package com.example.demo.rowmapper;


import com.example.demo.entity.Favorite;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FavoriteRowMapper implements RowMapper<Favorite> {

    @Override
    public Favorite mapRow(ResultSet rs, int rowNum) throws SQLException {
        Favorite favorite = new Favorite();

        favorite.setUser_id(rs.getLong("user_id"));
        favorite.setProduct_id(rs.getLong("product_id"));
        favorite.setPrice_LB(rs.getDouble("price_LB"));

        return favorite;
    }
}
