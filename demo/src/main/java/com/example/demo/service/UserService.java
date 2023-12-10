package com.example.demo.service;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private final UserDao userDao;

    // 构造函数注入UserDao
    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    // 保存用户，并在需要时启用事务
    @Transactional
    public void saveUser(User user) {
        userDao.saveUser(user);
    }

    // 获取所有用户信息
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    // 获取特定用户信息
    public User getUserById(Long userId) {
        return userDao.getUserById(userId);
    }

    // 更新用户信息，并在需要时启用事务
    @Transactional
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    // 删除用户，并在需要时启用事务
    @Transactional
    public void deleteUser(Long userId) {
        userDao.deleteUser(userId);
    }

    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    public List<Map<String, Object>> getFavoriteProducts(String gender, Integer startAge, Integer endAge) {
        return userDao.getFavoriteProducts(gender, startAge, endAge);
    }

    public List<Map<String, Object>> getFavoriteCategoryRatio(String gender, Integer startAge, Integer endAge) {
        return userDao.getFavoriteCategoryRatio(gender, startAge, endAge);
    }
}
