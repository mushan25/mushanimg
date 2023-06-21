package com.hzb.file.executor.service;

import com.hzb.base.redis.service.RedisService;
import com.hzb.file.api.ProcessService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author: hzb
 * @Date: 2023/6/19
 */
@Slf4j
@ExtendWith(MockitoExtension.class)
class BaiduProcessServiceImplTest {
    private ProcessService underTest;

    @Mock
    private RedisService redisService;

    @BeforeEach
    void setUp() {
        underTest = new ProcessServiceImpl();
    }

    @Test
    void canSetAccessToken() {
        ;
    }

    @Test
    void canImageReview(){

    }
}