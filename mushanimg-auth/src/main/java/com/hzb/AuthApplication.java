package com.hzb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author: hzb
 * @Date: 2023/4/10
 */
@SpringBootApplication
public class AuthApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(AuthApplication.class, args);
        }catch (Throwable e){
            e.printStackTrace();
        }
    }
}
