package org.example.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.example.order.bean.Order;
import org.example.order.feign.WeatherFeignClient;
import org.example.order.properties.OrderProperties;
import org.example.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;


//@RequestMapping("/api/order")//有过滤器就不需要添加@RequestMapping
//跨域问题
//@CrossOrigin//所有接口允许前端跨域访问，由于多个服务和controller都要写跨域，所以放在网关的配置文件解决跨域问题
@RefreshScope //动态刷新配置,
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    OrderProperties orderProperties;

    @Autowired
    WeatherFeignClient weatherFeignClient;


    @GetMapping("/config")
    public String getConfig(){
        return "order.timeout="+ orderProperties.getTimeOut()+" order.order-confirm="+orderProperties.getAutoConfirm()+
                "order.db-url="+ orderProperties.getDbUrl();
    }

    //创建订单
    @GetMapping("/create")
    public Order getOrder(@RequestParam("userId") Long userId ,
                           @RequestParam("productId") Long productId) {
        Order order=orderService.createOrder(userId, productId);

        return order;
    }

    @GetMapping("/seckill")
    public Order seckill(@RequestParam("userId") Long userId ,
                          @RequestParam("productId") Long productId) {
        Order order=orderService.createOrder(userId, productId);
        order.setId(Long.MAX_VALUE);
        return order;
    }

    @GetMapping("/read")
    public String read(){
        return "read";
    }


    @GetMapping("/write")
    public String write(){
        return "write";
    }


}
