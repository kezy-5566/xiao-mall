package xyz.vimtool.config.shiro;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.vimtool.commons.response.HttpResponse;
import xyz.vimtool.commons.response.MarkCode;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

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
     * 验证码
     */
    private static final String CODE = "code";

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
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        // 图片验证码验证
        if ("972536".equals(WebUtils.toHttp(request).getParameter(CODE))) {
            return true;
        }

        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        // 设置浏览器用utf8来解析返回的数据
        httpResponse.setHeader("Content-type", "text/html;charset=UTF-8");
        // 设置servlet用UTF-8转码，而不是用默认的ISO8859
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.getWriter().write(JSON.toJSONString(HttpResponse.build(MarkCode.build("图片验证码错误"))));
        return false;
    }
}
