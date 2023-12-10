package com.example.demo.dao;

import com.example.demo.entity.MsgList;
import com.example.demo.rowmapper.MsgListRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class MsgListDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MsgListDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 通过用户 ID 查询 MsgList
    public List<MsgList> getMsgListByUserId(Long userId) {
        String sql = "SELECT * FROM msg_list WHERE user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, new MsgListRowMapper());
    }

    // 添加新的消息
    public int addMsgList(MsgList msgList) {
        String sql = "INSERT INTO msg_list (user_id, content, time) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, msgList.getUserId(), msgList.getContent(), msgList.getTime());
    }
}
