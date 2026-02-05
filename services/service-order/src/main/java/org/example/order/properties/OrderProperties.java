package org.example.order.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


//读取nacos中的配置文件
@Component
@ConfigurationProperties(prefix = "order")
@Data
public class OrderProperties {
    String TimeOut;
    String autoConfirm;
    String dbUrl;
}
