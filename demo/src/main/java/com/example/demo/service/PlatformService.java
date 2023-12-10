package com.example.demo.service;

import com.example.demo.dao.PlatformDao;
import com.example.demo.entity.Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlatformService {
    private final PlatformDao platformDao;
    @Autowired
    public PlatformService(PlatformDao platformDao) {
        this.platformDao = platformDao;
    }

    public List<Platform> getAllPlatforms() {
        return platformDao.getAllPlatforms();
    }

    public Platform getPlatformById(Long platformId) {
        return platformDao.getPlatformById(platformId);
    }

    public Platform getPlatformByName(String name) {
        return platformDao.getPlatformByName(name);
    }
}
