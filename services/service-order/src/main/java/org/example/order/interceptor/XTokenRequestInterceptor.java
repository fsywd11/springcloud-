package org.example.order.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

//自动实现feign提供的拦截器RequestInterceptor接口
//为所有的远程调用都添加X-Token请求头
@Component
public class XTokenRequestInterceptor implements RequestInterceptor {
    //请求拦截器
    @Override
    public void apply(RequestTemplate requestTemplate) {
        System.out.println("请求拦截器");
        requestTemplate.header("X-Token", UUID.randomUUID().toString());
    }
}
