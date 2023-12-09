package com.example.demo.service;

import com.example.demo.dao.PriceHistoryDao;
import com.example.demo.entity.PriceHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * @Description：
 * @Author ShangYu
 * @date 2023-12-09
 **/
@Service
public class PriceHistoryService {
    private final PriceHistoryDao priceHistoryDao;
    @Autowired
    public PriceHistoryService(PriceHistoryDao priceHistoryDao) {
        this.priceHistoryDao = priceHistoryDao;
    }

    public void savePriceHistory(Long productId, double price , Date date) {
        priceHistoryDao.savePriceHistory(new PriceHistory( productId, date, price));
    }

    public List<PriceHistory> getAllPriceHistorys() {
        return priceHistoryDao.getAllPriceHistorys();
    }

    public List<PriceHistory> getAllPriceHistorysByProductId(Long productId, String timespan) {
        return priceHistoryDao.getPriceHistorysByProductId(productId, timespan);
    }

    public void deletePriceHistory(Long priceHistoryId) {
        priceHistoryDao.deletePriceHistory(priceHistoryId);
    }


}
