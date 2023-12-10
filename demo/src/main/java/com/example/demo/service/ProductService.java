package com.example.demo.service;

import com.example.demo.dao.ProductDao;
import com.example.demo.entity.Product;
import com.example.demo.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductDao productDao;

    @Autowired
    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void saveProduct(Product product) {
        productDao.saveProduct(product);
    }

    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    public Product getProductById(Long productId) {
        return productDao.getProductById(productId);
    }

    public List<Product> getProductsByCategory(String category) {
        return productDao.getProductsByCategory(category);
    }

    public void updateProduct(Product product) {
        productDao.updateProduct(product);
    }

    public Result deleteProduct(Long productId) {
        return productDao.deleteProduct(productId);
    }

    public List<Product> searchProductsByKeyword(String keyword) {
        return productDao.searchProductsByKeyword(keyword);
    }

    public List<Product> getProductsByMerchantId(Long merchantId) {
        return productDao.getProductsByMerchantId(merchantId);
    }

}
