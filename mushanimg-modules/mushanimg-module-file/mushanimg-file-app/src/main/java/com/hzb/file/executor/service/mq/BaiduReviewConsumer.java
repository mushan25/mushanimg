package com.hzb.file.executor.service.mq;

import com.hzb.file.api.ProcessService;
import com.hzb.file.domain.image.gateway.ImageGateway;
import com.hzb.file.domain.image.model.entities.Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author: hzb
 * @Date: 2023/6/20
 */
@Service
@RocketMQMessageListener(
        topic = "review",
        consumeThreadNumber = 2,
        maxReconsumeTimes = 3,
        consumerGroup = "consumer_test"
)
@Slf4j
@RequiredArgsConstructor
public class BaiduReviewConsumer implements RocketMQListener<Image> {
    @Value("${mushanimg.illegal-image-path}")
    private String illegalImagePath;
    private static final long TIMEOUT = 1000;
    private final ProcessService processService;
    private final ImageGateway imageGateway;

    @Override
    public void onMessage(Image image) {
        try {
            long startTime = System.currentTimeMillis();
            if(processService.baiduImageReview(image.getImgurl())){
                image.setLocalFilePath(illegalImagePath);
                imageGateway.upload2Minio(image);
            }
            long reviewTime = System.currentTimeMillis() - startTime;
            if (reviewTime < TIMEOUT) {
                Thread.sleep(TIMEOUT - reviewTime);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
