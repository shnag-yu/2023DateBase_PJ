package com.example.demo.controller;

import com.example.demo.entity.Favorite;
import com.example.demo.service.FavoriteService;
import com.example.demo.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {
    private final FavoriteService favoriteService;

    @Autowired
    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    // 收藏商品
    @PostMapping("/add")
    public Result<Favorite> addFavorite(@RequestBody Favorite favorite) {
        Favorite existingFavorite = favoriteService.getFavoriteByUserIdAndProductId(favorite.getUser_id(), favorite.getProduct_id());
        if (existingFavorite != null) {
            return Result.error(400, "商品已被收藏");
        }
        favoriteService.addFavorite(favorite);
        return Result.success(favorite);
    }

    // 为喜欢的商品设置价格下限
    @PostMapping("/setPriceLimit")
    public Result<Favorite> setPriceLimit(@RequestBody Favorite favorite) {
        Favorite existingFavorite = favoriteService.getFavoriteByUserIdAndProductId(favorite.getUser_id(), favorite.getProduct_id());
        if (existingFavorite == null) {
            return Result.error(404, "商品未被收藏");
        }
        existingFavorite.setPrice_LB(favorite.getPrice_LB());
        favoriteService.updateFavoritePriceLB(existingFavorite);
        return Result.success(existingFavorite);
    }

    // 通过用户 ID 和商品 ID 查询 Favorite
    @GetMapping("/get")
    public Result<Favorite> getFavorite(@RequestParam Long user_id, @RequestParam Long product_id) {
//        Long userId = favorite.getUser_id();
//        Long productId = favorite.getProduct_id();
        Favorite favorite1 = favoriteService.getFavoriteByUserIdAndProductId(user_id, product_id);
        if (favorite1 != null) {
            return Result.success(favorite1); // 成功时返回favorite数据
        }
        return Result.error(404, "Favorite not found"); // 失败时返回错误信息
    }

    // 通过用户 ID 查询 Favorite
    @GetMapping("/user/{userId}")
    public List<Favorite> getFavoritesByUserId(@PathVariable Long userId) {
        return favoriteService.getFavoritesByUserId(userId);
    }

}
