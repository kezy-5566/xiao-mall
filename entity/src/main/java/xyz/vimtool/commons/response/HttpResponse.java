package xyz.vimtool.commons.response;

import java.io.Serializable;

/**
 * HTTP 返回结果
 *
 * @author   qinxiaoqing
 * @version  1.0
 * @date     2017/08/24
 */
@SuppressWarnings("unchecked")
public class HttpResponse<T> implements Serializable {

    /**
     * 错误码
     */
    private int code;

    /**
     * 错误码描述
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    public static HttpResponse buildSuccess() {
        return build(HttpCode.SUCCESS);
    }

    public static <T> HttpResponse<T> buildSuccess(T data) {
        return build(HttpCode.SUCCESS, data);
    }

    public static HttpResponse buildFailed() {
        return build(HttpCode.FAILED);
    }

    public static HttpResponse build(ErrorCode errorCode) {
        return new HttpResponse(errorCode);
    }

    public static <T> HttpResponse<T> build(ErrorCode errorCode, T data) {
        return new HttpResponse(errorCode, data);
    }

    public static HttpResponse build(int code, String msg) {
        return new HttpResponse(code, msg);
    }

    public static <T> HttpResponse<T> build(int code, String msg, T data) {
        return new HttpResponse(code, msg, data);
    }

    private HttpResponse(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    private HttpResponse(ErrorCode errorCode, T data) {
        this(errorCode);
        this.data = data;
    }

    private HttpResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private HttpResponse(int code, String msg, T data) {
        this(code, msg);
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
