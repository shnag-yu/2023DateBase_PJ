package com.example.demo.controller;

import com.example.demo.entity.Merchant;
import com.example.demo.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/merchants")
public class MerchantController {

    private final MerchantService merchantService;

    @Autowired
    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    //查询自己的商家信息
    @GetMapping("/{id}")
    public ResponseEntity<Merchant> getMerchant(@PathVariable Long id) {
        Merchant merchant = merchantService.getMerchantById(id);
        return ResponseEntity.ok(merchant);
    }

    //新增商家
    @PostMapping("/register")
    public ResponseEntity<Merchant> addMerchant(@RequestBody Merchant merchant) {
        boolean isAdded = merchantService.addMerchant(merchant);
        if (isAdded) {
            return ResponseEntity.ok(merchant);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    //删除商家
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchant(@PathVariable Long id) {
        boolean isDeleted = merchantService.deleteMerchant(id);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
