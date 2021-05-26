package com.bootdo.common.constant;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

/**
 * @author rory.chen
 * @date 2021-01-07 14:24
 */
public class CommonConsts {

    public static final DateTimeFormatter DTF_DAY = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final DateTimeFormatter DTF_SECONDS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final SimpleDateFormat SDF_SECONDS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final String GEN_WINDOW_PATH = "D:\\Users\\rory.chen\\Desktop\\gen\\";

    public static final String ASC = "asc";

    public static final String AES = "AES";

    public static final String X_ACCESS_TOKEN = "x-access-token";

    public static final String USERINFO = "userInfo";

    public static final String OPENID = "openid";

    public static final String ERRMSG = "errmsg";

    public static final String SESSION_KEY = "session_key";

    public static final String DEFAULT_FLODER = "默认文件夹";

    public static final String ZERO_STR = "0";

    public static final String ONE_STR = "1";

    public static final Integer ZERO_INT = 0;

    public static final Integer ONE_INT = 1;

}
