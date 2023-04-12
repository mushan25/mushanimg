package com.hzb.gateway.service;

import com.hzb.base.core.exception.CaptchaException;
import com.hzb.base.core.web.domain.AjaxResult;

/**
 * @author: hzb
 * @Date: 2023/4/12
 */
public interface ValidateCodeService {

    /**
     * 生成验证码
     * @return AjaxResult
     * @throws CaptchaException 自定义验证码异常
     */
    AjaxResult createCaptcha() throws CaptchaException;

    /**
     * 校验验证码
     * @param key 验证码key
     * @param value 验证码值
     * @throws CaptchaException 自定义验证码异常
     */
    void checkCaptcha(String key, String value) throws CaptchaException;
}
