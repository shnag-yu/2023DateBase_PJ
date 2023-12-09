package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long productId;
    private String name;
    private String category;
    private String prodRegion;
    private String prodDate;
    private Double price;
    private Long merchantId;
    private Long platformId;
    private String description;

    // 构造函数、getter和setter等方法

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId) &&
                Objects.equals(name, product.name) &&
                Objects.equals(category, product.category) &&
                Objects.equals(prodRegion, product.prodRegion) &&
                Objects.equals(prodDate, product.prodDate) &&
                Objects.equals(price, product.price) &&
                Objects.equals(merchantId, product.merchantId) &&
                Objects.equals(platformId, product.platformId) &&
                Objects.equals(description, product.description);

    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, name, category, prodRegion, prodDate, price, merchantId, platformId, description);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", prodRegion='" + prodRegion + '\'' +
                ", prodDate='" + prodDate + '\'' +
                ", price=" + price +
                ", merchantId=" + merchantId +
                ", platformId=" + platformId +
                ", description='" + description + '\'' +
                '}';
    }
}
