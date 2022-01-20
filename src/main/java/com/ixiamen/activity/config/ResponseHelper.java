package com.ixiamen.activity.config;

import org.springframework.http.HttpStatus;

/**
 * 统一返回相应参数
 *
 * @author luoyongbin 53182347@qq.com
 */
public class ResponseHelper {

    public ResponseHelper() {
    }

    public static <T> ResponseModel notFound(String message) {
        ResponseModel response = new ResponseModel();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setCode(HttpStatus.NOT_FOUND.getReasonPhrase());
        response.setMessage(message);
        return response;
    }

    public static <T> ResponseModel internalServerError(String message) {
        ResponseModel response = new ResponseModel();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        response.setMessage(message);
        return response;
    }

    public static <T> ResponseModel<String> validationFailure(String message) {
        ResponseModel<String> response = new ResponseModel<>();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setCode(HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.setMessage(message);
        return response;
    }

    public static <T> ResponseModel unauthorizedFailure(String message) {
        ResponseModel response = new ResponseModel();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCode(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        response.setMessage(message);
        return response;
    }

    public static <T> ResponseModel<T> buildResponseModel(T result) {
        ResponseModel<T> response = new ResponseModel<>();
        response.setStatus(HttpStatus.OK.value());
        response.setCode(HttpStatus.OK.getReasonPhrase());
        response.setMessage(HttpStatus.OK.getReasonPhrase());
        response.setResult(result);
        return response;
    }
}
