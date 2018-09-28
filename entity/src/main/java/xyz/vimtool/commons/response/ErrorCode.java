package xyz.vimtool.commons.response;

/**
 * 错误码
 *
 * @author    qinxiaoqing
 * @date      2017/04/15
 * @version   1.0
 */
public interface ErrorCode {

    /**
     * 获取错误码
     *
     * @return 错误码
     */
    int getCode();

    /**
     * 获取错误信息
     *
     * @return 错误信息
     */
    String getMsg();
}
