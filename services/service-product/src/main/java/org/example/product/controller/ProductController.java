package org.example.product.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.product.bean.Product;
import org.example.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;
    //查询商品
    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable("id") Long productId,
    HttpServletRequest request) {
        System.out.println("请求头："+request.getHeader("X-Token"));
        System.out.println("查询商品id="+productId);
        Product product = productService.getProductById(productId);
        return product;
    }
}
