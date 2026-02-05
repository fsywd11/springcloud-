package org.example.order.config;

import feign.Logger;
import feign.Retryer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class OrderServiceConfig {


    //自动进行feign重试，默认5次
    @Bean
    Retryer feignRetryer() {
        return new Retryer.Default();
    }

    //自动打印feign日志
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @LoadBalanced//解决负载均衡
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
        //远程调用对象
    }

}
