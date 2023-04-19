package com.hzb.auth.controller;

import com.hzb.auth.service.GrpcClientTest;
import com.hzb.base.core.web.domain.AjaxResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hzb
 * @date 2023/4/20 0:57
 */
@RestController
public class TestController {
    private final GrpcClientTest grpcClientTest;

    public TestController(GrpcClientTest grpcClientTest) {
        this.grpcClientTest = grpcClientTest;
    }
    @GetMapping("/user")
    public AjaxResult hello(String userName){
        return grpcClientTest.test(userName);
    }
}
