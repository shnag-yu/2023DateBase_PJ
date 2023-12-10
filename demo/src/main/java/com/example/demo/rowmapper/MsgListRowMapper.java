package com.example.demo.rowmapper;

import com.example.demo.entity.MsgList;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MsgListRowMapper implements RowMapper<MsgList>  {
    @Override
    public MsgList mapRow(ResultSet rs, int rowNum) throws SQLException {
        MsgList message = new MsgList();
        message.setMsgId(rs.getLong("msg_id"));
        message.setUserId(rs.getLong("user_id"));
        message.setContent(rs.getString("content"));
        message.setTime(rs.getTimestamp("time"));
        return message;
    }
}

