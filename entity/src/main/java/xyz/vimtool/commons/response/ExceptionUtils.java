package xyz.vimtool.commons.response;

/**
 * 异常工具类
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/19
 */
public class ExceptionUtils {

    public static void throwResponseException(ErrorCode errorCode) throws ResponseException {
        throw new ResponseException(errorCode);
    }
}
