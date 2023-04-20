package com.hzb.controller;

import com.hzb.service.GrpcClientTest;
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
    public void hello(String userName){
        grpcClientTest.test(userName);
    }
}
