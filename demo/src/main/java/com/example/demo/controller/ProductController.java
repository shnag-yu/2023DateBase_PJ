package com.example.demo.controller;

import com.example.demo.entity.PriceHistory;
import com.example.demo.entity.Product;
import com.example.demo.service.PlatformService;
import com.example.demo.service.PriceHistoryService;
import com.example.demo.service.ProductService;
import com.example.demo.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final PriceHistoryService priceHistoryService;
    private final PlatformService platformService;

    @Autowired
    public ProductController(ProductService productService, PriceHistoryService priceHistoryService, PlatformService platformService) {
        this.productService = productService;
        this.priceHistoryService = priceHistoryService;
        this.platformService = platformService;
    }

    @PostMapping
    public void saveProduct(@RequestBody Product product) {
        product.setPlatformId(platformService.getPlatformByName(product.getPlatformName()).getId());
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
    public Result deleteProduct(@PathVariable Long productId) {
        return productService.deleteProduct(productId);
    }

    @GetMapping("/pricehistory/{productId}")
    public List<PriceHistory> getPriceHistory(@PathVariable Long productId, @RequestParam String timespan) {
//        System.out.println(timespan);
        return priceHistoryService.getAllPriceHistorysByProductId(productId,timespan);
    }

    @GetMapping("lowestprice/{productId}")
    public Double getLowestPrice(@PathVariable Long productId, @RequestParam String timespan) {
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

    @GetMapping("/maxPriceRanges")
    public List<Map<String, Object>> getMaxPriceRanges(@RequestParam String category,@RequestParam String timespan) {
        return priceHistoryService.getMaxPriceRanges(category, timespan);
    }

    @GetMapping("/minPriceRanges")
    public List<Map<String, Object>> getMinPriceRanges(@RequestParam String category,@RequestParam String timespan) {
        return priceHistoryService.getMinPriceRanges(category, timespan);
    }

    @GetMapping("/otherMerchants")
    public List<Map<String, Object>> getOtherMerchants(@RequestParam String product_name){
        return productService.getOtherMerchants(product_name);
    }

    @PutMapping("/pricehistory")
    public void updatePriceHistory(@RequestBody PriceHistory priceHistory) {
        priceHistoryService.updatePriceHistory(priceHistory);
    }
}
