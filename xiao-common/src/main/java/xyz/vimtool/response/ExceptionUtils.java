package xyz.vimtool.response;

import java.io.PrintWriter;
import java.io.StringWriter;

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

    public static String getTrace(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }
}
