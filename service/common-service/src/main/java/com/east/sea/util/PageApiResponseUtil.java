package com.east.sea.util;

import com.east.sea.common.ApiResponse;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="PageApiResponse",description="数据响应规范结构")
public class PageApiResponseUtil<T> extends ApiResponse<T> implements Serializable {

    public static ApiResponse<T> buildPageApiResponse(Integer code, String message, T data) {
        PageApiResponseUtil<T> response = new PageApiResponseUtil<>();
        response.setCode(code);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

}
