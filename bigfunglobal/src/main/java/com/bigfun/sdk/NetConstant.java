package com.bigfun.sdk;

public interface NetConstant {
    String BIGFUN_BASE_URL = "https://bigfun.xiaoxiangwan.com/api/";
    String BMARTPAY_BASE_URL = "https://pay.bmartpay.com/api/";
    String PLATFORM_BASE_URL = "http://platformapi.xiaoxiangwan.com/api/";
    String BIGFUN_SMS_URL = "http://cloud.awsdefense.com:8120/api/";

    /**
     * 登录
     */
    String LOGIN = BIGFUN_BASE_URL + "sdkuser/blogin";

    /**
     * 发送验证码
     */
//    String SEND_SMS = BIGFUN_SMS_URL + "auth/sendsms";
    String SEND_SMS = BIGFUN_BASE_URL + "sdkuser/sendsms";
    /**
     * SDK的配置
     */
    String BINFUN_SDK=BIGFUN_SMS_URL+"sdkConfiguration/getSdkConfigurationInfo";


    String REPORT_URL = BIGFUN_SMS_URL + "sdkData/dataReport";
}
