package org.example.order.feign;


import org.example.order.feign.fallback.ProductFeignClientFallback;
import org.example.product.bean.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@FeignClient(value = "service-product",fallback = ProductFeignClientFallback.class)//feign客户端，
public interface ProductFeignClient {

    //mvc注解的两套使用逻辑
    //标注在Controller类上，则表示当前类中所有方法都使用feign进行远程调用
    @GetMapping("/product/{id}")
    Product getProductById(@PathVariable("id") Long id);

}
