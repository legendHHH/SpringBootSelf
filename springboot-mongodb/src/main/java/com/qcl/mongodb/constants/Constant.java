package com.qcl.mongodb.constants;

public class Constant {

    /** 参数错误 */
    public static final int ERROR_PARAM_CODE = 400;
    /** 登陆错误 */
    public static final int UN_AUTHORIZED = 401;
    /** 操作失败 */
    public static final int FAIL = 401;
    /** 非预期性内部错误 */
    public static final int EXCEPTION = 500;
    /** 业务异常 */
    public static final int ERROR_BUSINESS_CODE = 501;
    /** 数据源类型 */
    public static final String DATA_TYPE = "dataType";
    /** 返回数据格式,json */
    public static final String CONTENT_TYPE = "application/json; charset=utf-8";
    /** user信息 */
    public static final String USER_INFO = "user_info";
    /** token */
    public static final String AUTHORIZATION_TOKEN = "Authorization";
    /** agent */
    public static final String USER_AGENT = "user_agent";
    /** user_session_id */
    public static final String USER_SESSION_ID = "uid";

    public static final String AUTHORIZATION = "userToken";

    public static final String HEADER_IP = "user_ip";

    public static final String ANDROID_AGENT = "Android";

    public static final String TOKEN_PIC_CODE = "verifyCodeToken";

    private Constant() {}
}