package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.util.Result;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    // 构造函数注入UserService
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 处理获取所有用户的请求
    @GetMapping
    public Result<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return Result.success(users);
    }

    // 处理获取特定用户的请求
    @GetMapping("/{userId}")
    public Result<User> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return Result.error(404,"用户不存在");
        }
        return Result.success(user);
    }

    // 处理保存新用户的请求
    @PostMapping
    public void saveUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    // 处理更新用户信息的请求
    @PutMapping("/{userId}")
    public Result updateUser(@PathVariable Long userId, @RequestBody User user) {
        User existingUser = userService.getUserById(userId);
        if (existingUser != null) {
            // 更新用户信息
            existingUser.setName(user.getName());
            existingUser.setPassword(user.getPassword());
            existingUser.setAge(user.getAge());
            existingUser.setGender(user.getGender());
            existingUser.setTel(user.getTel());

            userService.updateUser(existingUser);
            return Result.success(existingUser);
        }
        return Result.error(400,"更新失败");
    }

    // 处理删除用户的请求
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
