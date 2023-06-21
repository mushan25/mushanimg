package com.hzb.file.api;

/**
 * @author: hzb
 * @Date: 2023/6/19
 */
public interface ProcessService {

    /**
     * 图片审核
     * @param url 本地图片路径
     * @return 图片审核结果
     */
    boolean baiduImageReview(String url);
}
