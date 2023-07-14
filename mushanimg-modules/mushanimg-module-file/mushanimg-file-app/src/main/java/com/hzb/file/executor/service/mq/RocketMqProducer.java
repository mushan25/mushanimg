package com.hzb.file.executor.service.mq;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author: hzb
 * @Date: 2023/7/14
 */
@Slf4j
@AllArgsConstructor
@Component
public class RocketMqProducer {

    private final RocketMQTemplate rocketMQTemplate;

    public  <T> void asyncSendMessage(T t, String reviewTopic) {
        Message<T> msg = MessageBuilder.withPayload(t).build();
        rocketMQTemplate.asyncSend(reviewTopic, msg, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("图片审核消息发送成功:{}", sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                log.info("图片审核消息发送失败:{}", throwable.getMessage());
            }
        });
    }
}
