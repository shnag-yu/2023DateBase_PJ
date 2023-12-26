package com.example.demo.service;

import com.example.demo.dao.ProductDao;
import com.example.demo.entity.Product;
import com.example.demo.dao.MerchantDao;
import com.example.demo.entity.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantService {

    private final MerchantDao merchantDao;

    private final ProductDao productDao;



    @Autowired
    public MerchantService(MerchantDao merchantDao , ProductDao productDao) {
        this.merchantDao = merchantDao;
        this.productDao = productDao;
    }

    // 查询自己的商家信息
    public Merchant getMerchantById(Long id) {
        return merchantDao.findById(id);
    }

    // 新增商家
    public boolean addMerchant(Merchant merchant) {
        return merchantDao.save(merchant) == 1;
    }

    //删除商家
    public boolean deleteMerchant(Long id) {
        // 找到该商户的所有商品ID
        List<Long> productIds = merchantDao.findProductIdsByMerchantId(id);
        for (Long productId : productIds) {
            productDao.deleteProduct(productId);
        }
        return merchantDao.deleteById(id) == 1;
    }

    //根据名字查询商家
    public Merchant getMerchantByName(String name) {
        return merchantDao.findByName(name);
    }

    //查询所有商家
    public List<Merchant> getAllMerchants() {
        return merchantDao.findAll();
    }

    //更新商家信息
    public void updateMerchant(Merchant merchant) {
        merchantDao.updateMerchant(merchant);
    }
}
