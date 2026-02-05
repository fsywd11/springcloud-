package org.example.order;

import org.example.order.feign.WeatherFeignClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WeatherTest {
    @Autowired
    WeatherFeignClient weatherFeignClient;
    @Test
    public void testWeather(){
        String weather = weatherFeignClient.getWeather("上海");
        System.out.println(weather);
    }

}
