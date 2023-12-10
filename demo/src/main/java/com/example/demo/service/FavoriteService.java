package com.example.demo.service;

import com.example.demo.dao.FavoriteDao;
import com.example.demo.entity.Favorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FavoriteService {
    private final FavoriteDao favoriteDao;

    @Autowired
    public FavoriteService(FavoriteDao favoriteDao) {
        this.favoriteDao = favoriteDao;
    }

    // 添加新的收藏
    @Transactional
    public void addFavorite(Favorite favorite) {
        favoriteDao.saveFavorite(favorite);
    }

    // 更新收藏的价格下限
    @Transactional
    public void updateFavoritePriceLB(Favorite favorite) {
        favoriteDao.updateFavoritePriceLB(favorite);
    }

    // 删除收藏
    @Transactional
    public void deleteFavorite(Long userId, Long productId) {
        favoriteDao.deleteFavorite(userId, productId);
    }

    // 通过用户 ID 和商品 ID 查询 Favorite
    public Favorite getFavoriteByUserIdAndProductId(Long userId, Long productId) {
        return favoriteDao.getFavoriteByUserIdAndProductId(userId, productId);
    }
}
