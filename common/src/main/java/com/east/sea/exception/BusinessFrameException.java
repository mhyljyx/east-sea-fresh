package com.east.sea.exception;

import com.east.sea.common.ApiResponse;
import com.east.sea.enums.BaseCode;
import lombok.Data;

/**
 * @program: 
 * @description: 业务异常
 * @author: tztang
 **/
@Data
public class BusinessFrameException extends BaseException {

	private Integer code;
	
	private String message;

	public BusinessFrameException() {
		super();
	}

	public BusinessFrameException(String message) {
		super(message);
	}
	
	
	public BusinessFrameException(String code, String message) {
		super(message);
		this.code = Integer.parseInt(code);
		this.message = message;
	}
	
	public BusinessFrameException(Integer code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}
	
	public BusinessFrameException(BaseCode baseCode) {
		super(baseCode.getMsg());
		this.code = baseCode.getCode();
		this.message = baseCode.getMsg();
	}
	
	public BusinessFrameException(ApiResponse apiResponse) {
		super(apiResponse.getMessage());
		this.code = apiResponse.getCode();
		this.message = apiResponse.getMessage();
	}

	public BusinessFrameException(Throwable cause) {
		super(cause);
	}

	public BusinessFrameException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
	}

	public BusinessFrameException(Integer code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
		this.message = message;
	}
}
