package com.hzb.gateway.service.impl;

import com.google.code.kaptcha.Producer;
import com.hzb.base.core.constant.CacheConstants;
import com.hzb.base.core.constant.Constants;
import com.hzb.base.core.exception.CaptchaException;
import com.hzb.base.core.web.domain.AjaxResult;
import com.hzb.base.redis.service.RedisService;
import com.hzb.gateway.config.properties.CaptchaProperties;
import com.hzb.gateway.service.ValidateCodeService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author: hzb
 * @Date: 2023/4/12
 */
@Service
@RequiredArgsConstructor
public class ValidateCodeServiceImpl implements ValidateCodeService {
    private static final String CAPTCHA_TYPE_MATH = "math";
    private static final String CAPTCHA_TYPE_CHAR = "char";
    private static final String CAPTCHA_SUFFIX = "jpg";

    private final Producer captchaProducer;
    private final Producer captchaProducerMath;
    private final RedisService redisService;
    private final CaptchaProperties captchaProperties;

    @Override
    public AjaxResult createCaptcha() throws CaptchaException {
        AjaxResult ajax = AjaxResult.success();
        Boolean captchaEnabled = captchaProperties.getEnabled();
        ajax.put("captchaEnabled", captchaEnabled);
        if (!captchaEnabled){
            return ajax;
        }

        // 保存验证码信息
        String uuid = UUID.randomUUID().toString();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        String capStr = null;
        String code = null;
        BufferedImage image = null;

        String captchaType = captchaProperties.getType();
        if (CAPTCHA_TYPE_MATH.equals(captchaType)){
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        }else if (CAPTCHA_TYPE_CHAR.equals(captchaType)){
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisService.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, CAPTCHA_SUFFIX, os);
        }catch (IOException e){
            return AjaxResult.error(e.getMessage());
        }
        ajax.put("uuid", uuid);
        ajax.put("img", Base64.encodeBase64String(os.toByteArray()));

        return ajax;
    }

    @Override
    public void checkCaptcha(String code, String uuid) throws CaptchaException {
        if (StringUtils.isEmpty(code)){
            throw new CaptchaException("验证码不能为空");
        }
        if (StringUtils.isEmpty(uuid)){
            throw new CaptchaException("验证码已失效");
        }
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisService.getCacheObject(verifyKey);
        redisService.deleteObject(verifyKey);

        if (!code.equalsIgnoreCase(captcha)){
            throw new CaptchaException("验证码错误");
        }
    }
}
