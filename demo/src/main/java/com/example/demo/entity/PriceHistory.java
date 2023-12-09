package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceHistory {
    private Long productId;
    private java.sql.Date date;
    private Double price;

    // 构造函数、getter和setter等方法

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceHistory that = (PriceHistory) o;
        return Objects.equals(productId, that.productId) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, date);
    }

    @Override
    public String toString() {
        return "PriceHistory{" +
                "productId=" + productId +
                ", date=" + date +
                ", price=" + price +
                '}';
    }
}
