package com.example.demo.service;
import com.example.demo.dao.MerchantDao;
import com.example.demo.entity.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantService {

    private final MerchantDao merchantDao;

    @Autowired
    public MerchantService(MerchantDao merchantDao) {
        this.merchantDao = merchantDao;
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
        return merchantDao.deleteById(id) == 1;
    }

    //根据名字查询商家
    public Merchant getMerchantByName(String name) {
        return merchantDao.findByName(name);
    }
}
