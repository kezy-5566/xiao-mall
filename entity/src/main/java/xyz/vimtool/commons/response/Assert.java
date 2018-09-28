package xyz.vimtool.commons.response;


import java.util.Collection;
import java.util.regex.Pattern;

/**
 * 断言类
 *
 * @author    qinxiaoqing
 * @date      2017/04/15
 * @version   1.0
 */
public class Assert {

    /**
     * 断言为null
     *
     * @param errorCode 错误码
     * @param args      断言参数
     */
    public static void isNull(ErrorCode errorCode, Object ... args) {
        if (null != args) {
            for (Object arg : args) {
                if (null != arg) {
                    ExceptionUtils.throwResponseException(errorCode);
                }
            }
        }
    }

    /**
     * 断言不为null
     *
     * @param errorCode 错误码
     * @param args      断言参数
     */
    public static void notNull(ErrorCode errorCode, Object ... args) {
        if (null != args) {
            for(Object arg : args){
                if (null == arg) {
                    ExceptionUtils.throwResponseException(errorCode);
                }
            }
        }
    }

    /**
     * 断言两个对象相等
     *
     * @param errorCode  错误码
     * @param object1    比较对象
     * @param object2    比较对象
     */
    public static void isEquals(ErrorCode errorCode, Object object1, Object object2) {
        if (!object1.equals(object2)) {
            ExceptionUtils.throwResponseException(errorCode);
        }
    }

    /**
     * 断言为真
     *
     * @param errorCode  错误码
     * @param expression 断言条件
     */
    public static void isTrue(ErrorCode errorCode, boolean expression) {
        if (!expression) {
            ExceptionUtils.throwResponseException(errorCode);
        }
    }

    /**
     * 断言匹配正则表达
     *
     * @param errorCode  错误码
     * @param text       待校验字符串
     * @param regex      正则表达式
     */
    public static void isMatches(ErrorCode errorCode, String text, String regex) {
        if (!Pattern.compile(regex).matcher(text).matches()) {
            ExceptionUtils.throwResponseException(errorCode);
        }
    }

    /**
     * 断言为手机号
     *
     * @param errorCode 错误码
     * @param text      手机号
     */
    public static void isMobile(ErrorCode errorCode, String text) {
        if(!RegexUtils.isMobile(text)) {
            ExceptionUtils.throwResponseException(errorCode);
        }
    }

    /**
     * 断言对象在集合中
     *
     * @param errorCode 错误码
     * @param object    对象
     * @param objects   对象集合
     */
    public static void isInclude(ErrorCode errorCode, Object object, Object ... objects) {
        if (null == objects || null == object) {
            ExceptionUtils.throwResponseException(errorCode);
        }

        boolean in = false;
        for(Object o : objects){
            if (null == o) {
                ExceptionUtils.throwResponseException(errorCode);
            }

            if (o instanceof Collection) {
                Collection collection = (Collection) o;
                in = collection.contains(object);
            }

            if (object.equals(o)) {
                in = true;
            }

            if (in) {
                break;
            }
        }

        if (!in) {
            ExceptionUtils.throwResponseException(errorCode);
        }
    }
}
