package com.hzb.file.factory;

import com.baidu.aip.contentcensor.AipContentCensor;
import com.hzb.base.core.constant.Constants;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author: hzb
 * @Date: 2023/6/19
 */

@NoArgsConstructor
public class BaiduProcessClient {

    private volatile static AipContentCensor INSTANCE;
    public static AipContentCensor getInstance(){
        if (!Objects.nonNull(INSTANCE)){
            synchronized (BaiduProcessClient.class){
                if (!Objects.nonNull(INSTANCE)){
                    INSTANCE = new AipContentCensor(Constants.BAIDU_APP_ID, Constants.BAIDU_API_KEY, Constants.BAIDU_SECRET_KEY);
                }
            }
        }
        return INSTANCE;
    }
}
