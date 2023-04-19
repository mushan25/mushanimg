package com.hzb.controller;

import com.hzb.service.GrpcClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hzb
 * @date 2023/4/19 0:35
 */
@RestController
public class GreeterController {
    private final GrpcClientService grpcClientService;

    public GreeterController(GrpcClientService grpcClientService) {
        this.grpcClientService = grpcClientService;
    }

    @GetMapping("/hello")
    public void hello(){
        grpcClientService.greeter();
    }
}
