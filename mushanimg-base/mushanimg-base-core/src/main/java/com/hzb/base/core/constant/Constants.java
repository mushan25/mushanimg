package com.hzb.base.core.constant;

/**
 * @author: hzb
 * @Date: 2023/4/12
 */
public class Constants {
    /**
     * 成功标记
     */
    public static final Integer SUCCESS = 200;

    /**
     * 失败标记
     */
    public static final Integer FAIL = 500;

    /**
     * 验证码有效期（分钟）
     */
    public static final long CAPTCHA_EXPIRATION = 2;

    public static final long USER_ROLE_ID = 2;

    public static final String MINIO_URL = "http://localhost:9000/";

    public static final int TOURIST_UPLOAD_SIZE = 1024 * 1024 * 5;
    public static final int AVATAR_SIZE = 1024 * 1024 *5;
    public static final long AVATAR_RULE = 123456789;
    public static final int USER_UPLOAD_SIZE = 1024 * 1024 * 10;

    public static final String TOURIST_OBJECT_NAME = "tourist";

    public static final Long MB_SIZE = 1024 * 1024L;
    public static final String BAIDU_API_KEY = "6YFDD4CGqInmK5ac9aW4Yfpl";
    public static final String BAIDU_SECRET_KEY = "yZgfYOznF0PXTEmLBjp6dzIvrK6nIXGi";
    public static final String BAIDU_APP_ID = "34755999";
    public static final String NOT_COMPLIANCE = "不合规";
    public static final long REDISSON_LOCK_LEASE_TIME = 20;
    public static final long REDISSON_LOCK_WAIT_TIME = 10;
    public static final long IMAGE_CLASS_MAX_COUNT = 10;
}
