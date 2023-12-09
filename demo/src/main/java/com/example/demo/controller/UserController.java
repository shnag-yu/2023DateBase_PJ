package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // 处理获取特定用户的请求
    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    // 处理保存新用户的请求
    @PostMapping
    public void saveUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    // 处理更新用户信息的请求
    @PutMapping("/{userId}")
    public void updateUser(@PathVariable Long userId, @RequestBody User user) {
        User existingUser = userService.getUserById(userId);
        if (existingUser != null) {
            // 更新用户信息
            existingUser.setName(user.getName());
            existingUser.setPassword(user.getPassword());
            existingUser.setAge(user.getAge());
            existingUser.setGender(user.getGender());
            existingUser.setTel(user.getTel());

            userService.updateUser(existingUser);
        }
        // 如果用户不存在，可以选择返回适当的响应或抛出异常
    }

    // 处理删除用户的请求
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
