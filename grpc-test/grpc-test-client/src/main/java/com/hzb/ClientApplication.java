package com.hzb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hzb
 * @date 2023/4/19 0:30
 */
@SpringBootApplication
public class ClientApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(ClientApplication.class, args);
        }catch (Throwable e){
            e.printStackTrace();
        }

    }
}
