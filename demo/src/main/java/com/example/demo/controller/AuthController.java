package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import com.example.demo.entity.Merchant;
import com.example.demo.service.MerchantService;

import com.example.demo.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final MerchantService merchantService;

    // 构造函数注入UserService
    @Autowired
    public AuthController(UserService userService, MerchantService merchantService) {
        this.userService = userService;
        this.merchantService = merchantService;
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

    //商家登录
    @PostMapping("/merchantlogin")
    public Result<Merchant> merchantlogin(@RequestBody Merchant merchant) {
        Merchant existingmerchant = merchantService.getMerchantByName(merchant.getName());
        if (existingmerchant != null) {
            if (existingmerchant.getPassword().equals(merchant.getPassword())) {
                return Result.success(existingmerchant);
            }
        }
        return Result.error(400,"用户名或密码错误");
    }

    //商家注册
    @PostMapping("/merchantregister")
    public Result merchantregister(@RequestBody Merchant merchant) {
        //检查用户名是否已存在
        Merchant existingmerchant = merchantService.getMerchantByName(merchant.getName());
        if (existingmerchant != null) {
            return Result.error(400, "用户名已存在");
        }
        merchantService.addMerchant(merchant);
        return Result.success();
    }
}
