package xyz.vimtool.commons.response;

/**
 * 提示错误码
 *
 * @author    qinxiaoqing
 * @date      2017/08/23
 * @version   1.0
 */
public class MarkCode implements ErrorCode {

    public static final int CODE = 10001;

    private String msg;

    private MarkCode(String mark) {
        this.msg = mark;
    }

    @Override
    public int getCode() {
        return CODE;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public static MarkCode build(String mark) {
        return new MarkCode(mark);
    }
}
