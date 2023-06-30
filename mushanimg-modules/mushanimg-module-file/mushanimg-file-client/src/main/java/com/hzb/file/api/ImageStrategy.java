package com.hzb.file.api;

import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.file.dto.ImgRemoveCmd;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: hzb
 * @Date: 2023/6/14
 */
public interface ImageStrategy {
    /**
     * 上传图片
     * @param img 图片
     * @return 上传结果
     */
    AjaxResult execute(MultipartFile img);

    /**
     * 删除图片
     * @param imgRemoveCmd 图片信息
     * @return 删除结果
     */
    AjaxResult execute(ImgRemoveCmd imgRemoveCmd);

    /**
     * 异步发送消息
     * @param t 消息内容
     * @param rocketMQTemplate rocketMQTemplate
     * @param reviewTopic 消息主题
     * @param log 日志
     * @param <T> 消息类型
     */
    default <T> void asyncSendMessage(T t, RocketMQTemplate rocketMQTemplate, String reviewTopic, Logger log) {
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
