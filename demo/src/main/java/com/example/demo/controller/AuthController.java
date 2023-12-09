package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    // 构造函数注入UserService
    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // 登录
    @PostMapping("/login")
    public Result<User> login(@RequestBody User user) {
        User existingUser = userService.getUserByName(user.getName());
        if (existingUser != null) {
            if (existingUser.getPassword().equals(user.getPassword())) {
                return Result.success(existingUser);
            }
        }
        return Result.error(400,"用户名或密码错误");
    }

    //注册
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        //检查用户名是否已存在
        User existingUser = userService.getUserByName(user.getName());
        if (existingUser != null) {
            return Result.error(400, "用户名已存在");
        }
        userService.saveUser(user);
        return Result.success();
    }
}
