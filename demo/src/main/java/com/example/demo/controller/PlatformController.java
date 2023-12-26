package com.example.demo.controller;

import com.example.demo.entity.Platform;
import com.example.demo.service.PlatformService;
import com.example.demo.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/platforms")
public class PlatformController {

    private final PlatformService platformService;

    @Autowired
    public PlatformController(PlatformService platformService) {
        this.platformService = platformService;
    }

    @GetMapping
    public List<Platform> getAllPlatforms() {
        return platformService.getAllPlatforms();
    }

    @GetMapping("/{id}")
    public Platform getPlatformById(@PathVariable Long id) {
        return platformService.getPlatformById(id);
    }

    @GetMapping("/name/{name}")
    public Platform getPlatformByName(@PathVariable String name) {
        return platformService.getPlatformByName(name);
    }

    @PutMapping("/{platform_id}")
    public void updatePlatform(@PathVariable Long platform_id, @RequestBody Platform platform) {
        platformService.updatePlatform(platform);
    }
}
