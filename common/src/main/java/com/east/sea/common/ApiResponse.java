package com.east.sea.common;

import com.east.sea.enums.BaseCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="ApiResponse",description="数据响应规范结构")
public class ApiResponse<T> implements Serializable {

    @ApiModelProperty(name="code", dataType = "Integer", value="响应码 0:成功 其余:失败")
    private Integer code;

    @ApiModelProperty(name="message", dataType = "String", value="错误信息")
    private String message;

    @ApiModelProperty(name="data", value="响应的具体数据")
    private T data;

    private ApiResponse() {}

    public static <T> ApiResponse<T> error(Integer code, String message) {
        ApiResponse<T> apiResponse = new ApiResponse<T>();
        apiResponse.code = code;
        apiResponse.message = message;
        return apiResponse;
    }

    public static <T> ApiResponse<T> error(String message) {
        ApiResponse<T> apiResponse = new ApiResponse<T>();
        apiResponse.code = -100;
        apiResponse.message = message;
        return apiResponse;
    }

    public static <T> ApiResponse<T> error(Integer code, T data) {
        ApiResponse<T> apiResponse = new ApiResponse<T>();
        apiResponse.code = code;
        apiResponse.data = data;
        return apiResponse;
    }

    public static <T> ApiResponse<T> error(BaseCode baseCode) {
        ApiResponse<T> apiResponse = new ApiResponse<T>();
        apiResponse.code = baseCode.getCode();
        apiResponse.message = baseCode.getMsg();
        return apiResponse;
    }

    public static <T> ApiResponse<T> error(BaseCode baseCode, T data) {
        ApiResponse<T> apiResponse = new ApiResponse<T>();
        apiResponse.code = baseCode.getCode();
        apiResponse.message = baseCode.getMsg();
        apiResponse.data = data;
        return apiResponse;
    }

    public static <T> ApiResponse<T> error() {
        ApiResponse<T> apiResponse = new ApiResponse<T>();
        apiResponse.code = -100;
        apiResponse.message = "系统错误，请稍后重试!";
        return apiResponse;
    }

    public static <T> ApiResponse<T> ok() {
        ApiResponse<T> apiResponse = new ApiResponse<T>();
        apiResponse.code = BaseCode.SUCCESS.getCode();
        apiResponse.message = BaseCode.SUCCESS.getMsg();
        return apiResponse;
    }

    public static <T> ApiResponse<T> ok(T t) {
        ApiResponse<T> apiResponse = new ApiResponse<T>();
        apiResponse.code = 0;
        apiResponse.setData(t);
        return apiResponse;
    }

}
