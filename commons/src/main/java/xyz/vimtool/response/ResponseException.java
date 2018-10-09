package xyz.vimtool.response;


/**
 * 业务异常
 *
 * @author    qinxiaoqing
 * @date      2017/04/15
 * @version   1.0
 */
public class ResponseException extends RuntimeException {

    private ErrorCode errorCode;

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public ResponseException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }

    public ResponseException(ErrorCode errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }
}
