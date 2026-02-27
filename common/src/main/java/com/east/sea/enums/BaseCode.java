package com.east.sea.enums;

/**
 * @program: 
 * @description: 接口返回code码
 * @author: tztang
 **/
public enum BaseCode {
    /**
     * 基础code码
     * */
    SUCCESS(0, "OK"),
    
    SYSTEM_ERROR(-1,"系统异常，请稍后重试"),

    UNAUTHORIZED(401, "认证失败"),

    FORBIDDEN(403, "权限不足"),
    
    UID_WORK_ID_ERROR(500,"uid_work_id设置失败"),
    
    NAME_PASSWORD_ERROR(501,"账号名或登录密码不正确"),
    
    INITIALIZE_HANDLER_STRATEGY_NOT_EXIST(502,"初始化操作策略不存在"),
    
    VERIFY_CAPTCHA_ID_NOT_EXIST(503,"校验验证码id不存在"),
    
    VERIFY_CAPTCHA_EMPTY(504,"二次校验验证码的参数为空"),
    
    POINT_JSON_EMPTY(505,"点坐标为空"),
    
    CAPTCHA_TOKEN_JSON_EMPTY(506,"验证码token为空"),
    
    LOAD_BALANCER_NOT_EXIST(507,"负载均衡器不存在"),
    
    SERVER_LIST_NOT_EXIST(508,"服务列表不存在"),
    
    TWO_PASSWORDS_DIFFERENT(509,"两次输入的密码不同"),
    
    MESSAGE_CONSUMER_NOT_EXIST(510,"messageConsumer实现不存在"),
    
    REDIS_STREAM_CONSUMER_TYPE_NOT_EXIST(511,"redisStream消费方式不存在"),
    
    CHANNEL_DATA_NOT_EXIST(512,"没有找到ChannelData"),
    
    OPERATION_IS_TOO_FREQUENT_PLEASE_TRY_AGAIN_LATER(513,"操作太频繁，请稍后再试"),
    
    THREAD_INTERRUPTED(514,"线程中断错误"),
    
    EXECUTE_TIME_OUT(515,"执行超时"),
    
    LOGIN_USER_NOT_EXIST(516,"用户没有登录"),
    
    ONLY_SIGNATURE_ACCESS_IS_ALLOWED(517,"只允许签名访问"),
    
    MOBILE_ERROR_COUNT_TOO_MANY(518,"手机号输入次数过多，请稍后重试"),
    
    EMAIL_ERROR_COUNT_TOO_MANY(519,"邮箱输入次数过多，请稍后重试"),
    
    RSA_SIGN_ERROR(10000,"rsa签名验证失败"),
    
    RSA_DECRYPT_ERROR(10001,"rsa解密失败"),
    
    RSA_ENCRYPT_ERROR(10002,"rsa加密失败"),
    
    AES_ERROR(10003,"aes验证失败"),
    
    CUSTOM_ENABLED_RULE_EMPTY(10004,"customEnabledRule为空"),
    
    I_LOAD_BALANCER_RULE_EMPTY(10005,"iLoadBalancer为空"),
    
    SERVER_LIST_EMPTY(10006,"serverList为空，请检查灰度代码或者服务是否异常下线"),
    
    CHANNEL_DATA(10050,"渠道数据为空"),
    
    ARGUMENT_EMPTY(10051,"基础参数为空"),
    
    HEAD_ARGUMENT_EMPTY(10052,"请求头基础参数为空"),
    
    CODE_EMPTY(10053,"code参数为空"),
    
    PARAMETER_ERROR(10054,"参数验证异常"),
    
    TOKEN_EXPIRE(10055,"token过期"),
    
    API_RULE_TRIGGER(10056,"用户调用太过频繁，稍后再试"),
    
    API_RULE_TIME_WINDOW_INTERSECT(10057,"已有的时间范围已经包含"),
    
    USER_AUTHENTICATION(10058,"用户已认证"),
    
    SUBMIT_FREQUENT(20000,"执行频繁，请稍后再试"),
    
    USER_MOBILE_AND_EMAIL_NOT_EXIST(20001,"用户手机和邮箱需要选择一个"),
    
    USER_MOBILE_EMPTY(20002,"用户手机号不存在"),

    SERVICE_AGENCY_EXIST(30001,"运营服务机构已存在"),

    SERVICE_AGENCY_NOT_EXIST(30002,"运营服务机构不存在"),

    COMMUNITY_EXIST(30003,"互联网单位已存在"),

    COMMUNITY_NOT_EXIST(30004,"互联网单位不存在"),
    
    USER_NOT_EXIST(30005,"用户不存在"),

    USER_EXIST(30006,"用户已存在"),
    
    PARENT_DEPT_NOT_EXIST(30007,"父级部门不存在"),
    
    DEPT_NOT_EXIST(30009,"部门不存在"),

    DEPT_EXIST(30009,"部门已存在"),

    ADMIN_NOT_EXIST(30010,"管理员不存在"),

    ADMIN_EXIST(30011,"管理员已存在"),

    ACCOUNT_PWD_ERROR(30012,"账号或密码错误"),
    
    RPC_API_RESPONSE_DATA_EMPTY(40009,"rpc服务返回数据为空"),
    
    COMPOSITE_NOT_EXIST(40012,"通用验证不存在"),
    
    USER_REGISTER_FREQUENCY(40013,"用户注册频繁"),
    
    PROGRAM_SHOW_TIME_NOT_EXIST(40014,"节目演出时间不存在"),
    
    ORDER_NOT_EXIST(40015,"订单不存在"),
    
    ORDER_CANCEL(40016,"订单已取消"),
    
    ORDER_PAY(40017,"订单已支付"),
    
    ORDER_REFUND(40018,"订单已退单"),
    
    ORDER_CANAL_ERROR(40019,"订单取消失败"),
    
    ORDER_EXIST(40025,"订单存在"),
    
    CAN_NOT_CANCEL(40032,"订单不是未支付状态不能取消"),
    
    DELAY_QUEUE_CLIENT_NOT_EXIST(50001,"延迟队列客户端不存在"),
    
    DELAY_QUEUE_MESSAGE_NOT_EXIST(50002,"延迟队列消息不存在"),
    
    SEAT_IS_EXIST(50003,"该节目下座位以存在"),
    
    START_DATE_TIME_NOT_EXIST(50004,"开始时间为空"),
    
    END_DATE_TIME_NOT_EXIST(50005,"结束时间为空"),
    
    PARAMETERS_CANNOT_BE_EMPTY(50007,"参数不允许都为空"),
    
    USER_LOG_IN_STATUS_ERROR(60001,"用户不是登录状态"),
    
    USER_LOG_IN(60002,"用户已登录"),
    
    USER_ID_EMPTY(60003,"用户id为空"),
    
    USER_EMPTY(60004,"用户不存在"),
    
    NOT_FOUND(60007,"not found api %s %s"),
    
    GENERATE_RSA_SIGN_ERROR(60008,"生成res签名验证失败"),

    USER_LOG_OUT(60009,"用户已注销"),
    
    USER_ID_NOT_EXIST(70001,"user_id的值不存在"),
    
    USER_EMAIL_NOT_EXIST(70002,"用户邮箱不存在"),

    OAUTH_CLIENT_NOT_EXIST(80001,"Oauth客户端信息不存在"),
    ;
    
    private final Integer code;
    
    private String msg = "";
    
    BaseCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public Integer getCode() {
        return this.code;
    }
    
    public String getMsg() {
        return this.msg == null ? "" : this.msg;
    }
    
    public static String getMsg(Integer code) {
        for (BaseCode re : BaseCode.values()) {
            if (re.code.intValue() == code.intValue()) {
                return re.msg;
            }
        }
        return "";
    }
    
    public static BaseCode getRc(Integer code) {
        for (BaseCode re : BaseCode.values()) {
            if (re.code.intValue() == code.intValue()) {
                return re;
            }
        }
        return null;
    }
}
