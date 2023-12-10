package com.example.demo.entity;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {
    private Long user_id;
    private Long product_id;
    private Double price_LB; // 用户为喜欢的商品设定的价格下限

    public Favorite(Long user_id, Long product_id) {
        this.user_id = user_id;
        this.product_id = product_id;
        this.price_LB = 0.0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favorite favorite = (Favorite) o;
        return Objects.equals(user_id, favorite.user_id) &&
                Objects.equals(product_id, favorite.product_id) &&
                Objects.equals(price_LB, favorite.price_LB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, product_id, price_LB);
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "user_id=" + user_id +
                ", product_id=" + product_id +
                ", price_lb=" + price_LB +
                '}';
    }
}
