package com.ixiamen.activity.config;

/*
  统一返回相应参数实体类
  @author luoyongbin
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Objects;

@ApiModel(value = "响应结果类")
/*@JsonInclude(value=JsonInclude.Include.NON_NULL)*/
public class ResponseModel<T> implements Serializable {
    private static final long serialVersionUID = -1241360949457314497L;
    @ApiModelProperty(value = "返回状态码")
    private int status;
    @ApiModelProperty(value = "响应对象")
    private T result;
    @ApiModelProperty(value = "返回信息描述")
    private String message;
    @ApiModelProperty(value = "返回码")
    private String code;

    public ResponseModel() {
        HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getResponse();
        assert response != null;
        response.setCharacterEncoding("UTF-8");
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getResult() {
        return this.result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String toString() {
        return "ResponseModel [status=" + this.status + ", result=" + this.result + ", message=" + this.message + ", code=" + this.code + "]";
    }
}
