package com.example.demo.controller;

import com.example.demo.entity.PriceHistory;
import com.example.demo.entity.Product;
import com.example.demo.service.PriceHistoryService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final PriceHistoryService priceHistoryService;

    @Autowired
    public ProductController(ProductService productService, PriceHistoryService priceHistoryService) {
        this.productService = productService;
        this.priceHistoryService = priceHistoryService;
    }

    @PostMapping
    public void saveProduct(@RequestBody Product product) {
        productService.saveProduct(product);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        return productService.getProductsByCategory(category);
    }

    @PutMapping("/{productId}")
    public void updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        // Ensure the product ID in the path matches the ID in the request body
        if (productId.equals(product.getProductId())) {
            productService.updateProduct(product);
        } else {
            // Handle mismatched IDs
            // You may choose to throw an exception or handle it in a way that makes sense for your application
        }
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }

    @GetMapping("/pricehistory/{productId}")
    public List<PriceHistory> getPriceHistory(@PathVariable Long productId) {
        return priceHistoryService.getAllPriceHistorysByProductId(productId);
    }

    @GetMapping("/search/keyword={keyword}")
    public List<Product> searchProducts(@PathVariable String keyword) {
        return productService.searchProductsByKeyword(keyword);
    }

}
