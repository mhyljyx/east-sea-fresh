package com.east.sea.genie.enums;

/**
 * @program: 
 * @description: 接口返回code码
 * @author: tztang
 **/
public enum TgBaseCode {
    /**
     * 旅小秘code码
     * */
    TG_TRIP_PLAN_NOT_EXIST(91000,"行程信息不存在"),
    TG_DESTINATION_DETAIL_NOT_EXIST(92000,"目的地不存在"),
    ;
    
    private final Integer code;
    
    private String msg = "";
    
    TgBaseCode(Integer code, String msg) {
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
        for (TgBaseCode re : TgBaseCode.values()) {
            if (re.code.intValue() == code.intValue()) {
                return re.msg;
            }
        }
        return "";
    }
    
    public static TgBaseCode getRc(Integer code) {
        for (TgBaseCode re : TgBaseCode.values()) {
            if (re.code.intValue() == code.intValue()) {
                return re;
            }
        }
        return null;
    }
}
