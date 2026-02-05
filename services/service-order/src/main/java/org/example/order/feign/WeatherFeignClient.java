package org.example.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "weather-client",url="http://apis.juhe.cn")
public interface WeatherFeignClient {

    @PostMapping(value = "/simpleWeather/query",
            consumes = "application/x-www-form-urlencoded")  // 明确指定请求体格式，匹配 API 要求
    String getWeather(
            @RequestParam("city") String city     // 城市参数名：city（支持名称/ID，替换原 cityId）
    );
}
