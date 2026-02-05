package org.example.order.exception;

import com.alibaba.csp.sentinel.adapter.spring.webmvc_v6x.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.common.R;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;


//创建限流异常处理类,属于全局处理
@Component
public class MyBlockExceptionHander implements BlockExceptionHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String resourceName, BlockException e) throws Exception {
        httpServletResponse.setStatus(500);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter printWriter = httpServletResponse.getWriter();

       R r = R.error(500,resourceName + "被sentinel限流了,原因：" + e.getClass());
       String json =objectMapper.writeValueAsString(r);
        printWriter.write(json);
        printWriter.flush();
        printWriter.close();
    }

}
