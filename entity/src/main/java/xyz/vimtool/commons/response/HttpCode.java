package xyz.vimtool.commons.response;

/**
 * HTTP 错误码
 *
 * @author   qinxiaoqing
 * @version  1.0
 * @date     2017/08/24
 */
public enum HttpCode implements ErrorCode {

    /**
     * 请求成功
     */
    SUCCESS(200, "请求成功"),

    /**
     * 登录失败
     */
    NO_LOGIN(401, "登录失败"),

    /**
     * 禁止访问
     */
    NO_AUTH(403, "禁止访问"),

    /**
     * 服务器异常
     */
    FAILED(500, "服务器异常"),
    ;

    /**
     * 错误码
     */
    private final int code;

    /**
     * 错误信息
     */
    private final String msg;

    HttpCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
