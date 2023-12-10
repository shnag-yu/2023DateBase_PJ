package com.example.demo.service;


import com.example.demo.dao.MsgListDao;
import com.example.demo.entity.MsgList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class MsgListService {
    private final MsgListDao msgListDao;

    @Autowired
    public MsgListService(MsgListDao msgListDao) {
        this.msgListDao = msgListDao;
    }

    // 通过用户 ID 查询 MsgList
    public List<MsgList> getUserMsgLists(Long userId) {
        return msgListDao.getMsgListByUserId(userId);
    }

    // 插入新的 MsgList
    public MsgList createMsgList(MsgList msgList) {
        int update = msgListDao.addMsgList(msgList);
        if(update == 1) {
            // 在实际应用中，您可能还需要处理ID的赋值，如果它是由数据库自动生成的
            return msgList;
        }
        return null;
    }
}
