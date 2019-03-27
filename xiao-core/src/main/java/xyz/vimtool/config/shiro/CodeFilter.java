package xyz.vimtool.config.shiro;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.vimtool.response.HttpResponse;
import xyz.vimtool.response.MarkCode;
import xyz.vimtool.global.Constant;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

/**
 * 登录验证码拦截器
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @date    2018/9/20
 */
public class CodeFilter extends AccessControlFilter {

    private static final Logger log = LoggerFactory.getLogger(CodeFilter.class);

    /**
     * 表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，如果允许访问返回true，否则false
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    /**
     * 表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse response) throws Exception {
        HttpServletRequest request = WebUtils.toHttp(servletRequest);

        // 获取生成的验证码
        String verifyCodeExpected = (String) request.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        // 获取用户输入的验证码
        String verifyCodeActual = request.getParameter(Constant.User.CODE);

        // 图片验证码验证
        if (verifyCodeExpected != null && verifyCodeExpected.equals(verifyCodeActual)) {
            return true;
        }

        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        // 设置浏览器用utf8来解析返回的数据
        httpResponse.setHeader("Content-type", "text/html;charset=UTF-8");
        // 设置servlet用UTF-8转码，而不是用默认的ISO8859
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.getWriter().write(JSON.toJSONString(HttpResponse.build(MarkCode.build("验证码错误"))));
        return false;
    }
}
