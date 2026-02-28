package com.east.sea.handler;

import com.east.sea.common.ApiResponse;
import com.east.sea.enums.BaseCode;
import com.east.sea.exception.BusinessFrameException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author tztang
 * @since 2026/02/27
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     * @param e 业务异常
     * @return 统一响应
     */
    @ExceptionHandler(BusinessFrameException.class)
    public ApiResponse<Void> handleBusinessException(BusinessFrameException e) {
        log.error("BusinessException: code={}, message={}", e.getCode(), e.getMessage());
        return ApiResponse.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理参数校验异常
     * @param e 参数校验异常
     * @return 统一响应
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
        return ApiResponse.error(BaseCode.PARAMETER_ERROR.getCode(), message);
    }

    /**
     * 处理参数校验异常
     * @param e 参数校验异常
     * @return 统一响应
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResponse<Void> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ApiResponse.error(BaseCode.HTTP_METHOD_NOT_SUPPORTED.getCode() ,e.getMessage());
    }

    /**
     * 处理权限不足异常，抛出给 Security 的 AccessDeniedHandler 处理
     */
    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(AccessDeniedException e) throws AccessDeniedException {
        throw e;
    }

    /**
     * 处理请求体读取异常（如 JSON 格式错误）
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResponse<Void> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("HttpMessageNotReadableException: ", e);
        return ApiResponse.error(BaseCode.PARAMETER_ERROR.getCode(), "请求参数格式错误或缺失");
    }

    /**
     * 处理系统异常
     * @param e 系统异常
     * @return 统一响应
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleException(Exception e) {
        log.error("SystemException: ", e);
        return ApiResponse.error("系统异常，请联系管理员");
    }

}
