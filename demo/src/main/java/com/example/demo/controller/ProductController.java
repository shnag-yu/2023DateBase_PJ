package com.example.demo.controller;

import com.example.demo.entity.PriceHistory;
import com.example.demo.entity.Product;
import com.example.demo.service.PriceHistoryService;
import com.example.demo.service.ProductService;
import com.example.demo.util.Result;
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
    public Result updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        System.out.println(product);
        if (productId.equals(product.getProductId())) {
            productService.updateProduct(product);
            return Result.success();
        } else {
            return Result.error(400, "商品不存在");
        }
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }

    @GetMapping("/pricehistory/{productId}")
    public List<PriceHistory> getPriceHistory(@PathVariable Long productId, @RequestParam String timespan) {
//        System.out.println(timespan);
        return priceHistoryService.getAllPriceHistorysByProductId(productId,timespan);
    }

    @GetMapping("lowestprice/{productId}")
    public Integer getLowestPrice(@PathVariable Long productId, @RequestParam String timespan) {
        return priceHistoryService.getLowestPrice(productId,timespan);
    }
    @GetMapping("/search/keyword={keyword}")
    public List<Product> searchProducts(@PathVariable String keyword) {
        return productService.searchProductsByKeyword(keyword);
    }

    @GetMapping("/merchant/{merchantId}")
    public List<Product> getProductsByMerchantId(@PathVariable Long merchantId) {
        return productService.getProductsByMerchantId(merchantId);
    }

}
