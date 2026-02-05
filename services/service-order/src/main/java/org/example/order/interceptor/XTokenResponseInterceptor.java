package org.example.order.interceptor;

import feign.InvocationContext;
import feign.Response;
import feign.ResponseInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class XTokenResponseInterceptor implements ResponseInterceptor {

    @Override
    public Object intercept(InvocationContext invocationContext, Chain chain) throws Exception {
        log.info("===== 进入响应拦截器 ====="); // 新增：确认拦截器触发

        // 1. 执行远程调用，获取响应
        Object result = chain.next(invocationContext); // 修复：使用next方法替代proceed

        // 2. 解析响应信息
        Response response = invocationContext.response();
        if (response != null) {
            log.info("响应状态码: {}", response.status());
            // 3. 处理响应头（例如获取自定义响应头）
            response.headers().forEach((key, values) -> {
                log.info("响应头 {}: {}", key, values);
            });
        }

        // 4. 可以对响应结果进行二次处理（例如解密、格式转换等）
        log.info("远程调用响应结果: {}", result);

        log.info("===== 离开响应拦截器 ====="); // 新增：确认调用完成

        return result;
    }
}
