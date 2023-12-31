package com.example.demo.controller;

import com.example.demo.entity.Merchant;
import com.example.demo.service.MerchantService;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/merchants")
public class MerchantController {

    private final MerchantService merchantService;
    private final ProductService ProductService;

    @Autowired
    public MerchantController(MerchantService merchantService, ProductService ProductService) {
        this.merchantService = merchantService;
        this.ProductService = ProductService;
    }

    //查询所有商家
    @GetMapping("/allmerchant")
    public List<Merchant> getAllMerchants() {
        return merchantService.getAllMerchants();
    }
    //查询自己的商家信息
    @GetMapping("/merchantinfo/{id}")
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

    //查找所有商品
    @GetMapping("/merchant/{merchantId}")
    public List<Product> getProductsByMerchantId(@PathVariable Long merchantId) {
        return ProductService.getProductsByMerchantId(merchantId);
    }


    //更新商家信息
    @PutMapping("/{merchantId}")
    public void updateMerchant(@PathVariable Long merchantId, @RequestBody Merchant merchant) {
        merchant.setId(merchantId);
        merchantService.updateMerchant(merchant);
    }
}
