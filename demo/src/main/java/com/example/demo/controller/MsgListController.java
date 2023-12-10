package com.example.demo.controller;

import com.example.demo.entity.MsgList;
import com.example.demo.service.MsgListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MsgListController {
    private final MsgListService msgListService;

    @Autowired
    public MsgListController(MsgListService msgListService) {
        this.msgListService = msgListService;
    }

    // 通过用户 ID 查询 MsgList
    @GetMapping("/{userId}")
    public List<MsgList> getUserMsgLists(@PathVariable Long userId) {
        return msgListService.getUserMsgLists(userId);
    }


    // 插入新的 MsgList
    @PostMapping("/add")
    public MsgList createMsgList(@RequestBody MsgList msgList) {
        return msgListService.createMsgList(msgList);
    }

}
