package com.example.demo.service;

import com.example.demo.dao.PriceHistoryDao;
import com.example.demo.entity.PriceHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

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


    public Double getLowestPrice(Long productId, String timespan) {
        return priceHistoryDao.getLowestPrice(productId, timespan);
    }

    public List<Map<String, Object>> getMaxPriceRanges(String category, String timespan) {
        return priceHistoryDao.getMaxPriceRanges(category, timespan);
    }

    public List<Map<String, Object>> getMinPriceRanges(String category, String timespan) {
        return priceHistoryDao.getMinPriceRanges(category, timespan);
    }
}
