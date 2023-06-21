package com.hzb.file.executor.service;

import com.baidu.aip.contentcensor.AipContentCensor;
import com.baidu.aip.contentcensor.EImgType;
import com.hzb.base.core.constant.Constants;
import com.hzb.file.api.ProcessService;
import com.hzb.file.factory.BaiduProcessClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author: hzb
 * @Date: 2023/6/19
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class ProcessServiceImpl implements ProcessService {
    @Override
    public boolean baiduImageReview(String url) {
        AipContentCensor client = BaiduProcessClient.getInstance();
        JSONObject jsonObject = client.imageCensorUserDefined(url, EImgType.URL, null);
        log.info("百度图片审核结果:{}", jsonObject);

        if (!jsonObject.isNull("error_code")) {
            log.error("百度图片审核失败:{}", jsonObject);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return true;
        }

        return Constants.NOT_COMPLIANCE.equals(jsonObject.get("conclusion"));
    }
}
