package org.example.order.service.impl;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.example.order.bean.Order;
import org.example.order.feign.ProductFeignClient;
import org.example.order.service.OrderService;
import org.example.product.bean.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class OrderImpl implements OrderService {

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    RestTemplate restTemplate;

   @Autowired
    LoadBalancerClient loadBalancerClient;


   @Autowired
    ProductFeignClient productFeignClient;


    @SentinelResource(value = "CreateOrder", blockHandler = "CreateOrderFallback")
    @Override
    public Order createOrder(Long userId, Long productId) {
//        Product product =getProductFromRemoteWithLoadBalancerAnnotation(productId);
        Product product = productFeignClient.getProductById(productId);
        Order order=new Order();
        order.setId(1L);
        order.setTotalAmount(product.getPrice().multiply(new BigDecimal(product.getNum())));
        order.setUserId(0L);
        order.setNickName("fushiyu");
        order.setAddress("chongqin");
        //远程查询商品列表
        order.setProductList(Arrays.asList( product));

        return order;

    }

    //降级处理,兜底回调，放弃复杂化，返回一个简单对象
    public Order CreateOrderFallback(Long userId, Long productId, BlockException e){
        Order order=new Order();
        order.setId(1L);
        order.setTotalAmount(new BigDecimal("0"));
        order.setUserId(userId);
        order.setNickName("未知用户");
        order.setAddress("位置"+e.getClass());
        return order;
    }


    //discoveryClient服务发现远程调用，白雪
    private Product getProductFromRemote(Long productId){
        List<ServiceInstance> instances =discoveryClient.getInstances("service-product");
        ServiceInstance instance=instances.get(0);
        String url="http://"+instance.getHost()+":"+instance.getPort()+"/product/"+productId;
        log.info("url="+url);
        Product product = restTemplate.getForObject(url, Product.class);

        return product;
    }

    //完成负载均衡远程调用，白雪
    private Product getProductFromRemoteWithLoadBalancer(Long productId){
       ServiceInstance instance=loadBalancerClient.choose("service-product");
        String url="http://"+instance.getHost()+":"+instance.getPort()+"/product/"+productId;
        log.info("url="+url);
        Product product = restTemplate.getForObject(url, Product.class);

        return product;
    }


    //基于注解恶的负载均衡远程调用，白雪
    private Product getProductFromRemoteWithLoadBalancerAnnotation(Long productId){
        String url="http://service-product/product/"+productId;
        //远程发送请求
        Product product = restTemplate.getForObject(url, Product.class);
        return product;
    }
}
