package xyz.vimtool.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import xyz.vimtool.commons.response.RegexUtils;

import javax.validation.constraints.Pattern;

/**
 * 登录请求参数
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @date    2018/9/26
 */
@Data
public class LoginReq {

    @Pattern(regexp = RegexUtils.CELL_PHONE_REGEX, message = "请填写正确的手机号")
    private String phone;

    @Length(min = 8, max = 32, message = "密码8-32位")
    private String password;
}
