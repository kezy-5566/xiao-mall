package xyz.vimtool.config.shiro;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.vimtool.response.HttpCode;
import xyz.vimtool.response.HttpResponse;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 验证登录拦截器
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @date    2018/9/20
 */
public class AuthFilter extends AuthenticatingFilter {

    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);

    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        //获取请求token
        String token = getRequestToken(request);
        return new UsernamePasswordToken(token, (String) null, request.getRemoteHost());
    }

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
        //获取请求token，如果token不存在，直接返回401
        String token = getRequestToken(request);

        if (token == null || "".equals(token.trim())) {
            HttpServletResponse httpResponse = WebUtils.toHttp(response);
            // 设置浏览器用utf8来解析返回的数据
            httpResponse.setHeader("Content-type", "text/html;charset=UTF-8");
            // 设置servlet用UTF-8转码，而不是用默认的ISO8859
            httpResponse.setCharacterEncoding("UTF-8");
            try (PrintWriter writer = httpResponse.getWriter()) {
                writer.write(JSON.toJSONString(HttpResponse.build(HttpCode.NO_LOGIN)));
            } catch (IOException e) {
                e.printStackTrace();
                log.error("login exception = ", e);
            }
            return false;
        }

        // 创建AuthenticationToken
        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e,
                                     ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        // 设置浏览器用utf8来解析返回的数据
        httpResponse.setHeader("Content-type", "text/html;charset=UTF-8");
        // 设置servlet用UTF-8转码，而不是用默认的ISO8859
        httpResponse.setCharacterEncoding("UTF-8");
        try (PrintWriter writer = httpResponse.getWriter()) {
            // 处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            log.error("login exception = {}", throwable.getMessage());

            String json = "Not_Login";
            writer.print(json);
        } catch (IOException e1) {
            log.error("login exception = ", e1);
        }

        return false;
    }

    /**
     * 获取请求的token
     * 先获取请求头中的token，如无则获取请求参数中的token
     *
     * @param request http请求
     * @return token字符串
     */
    private String getRequestToken(ServletRequest request) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);

        //从header中获取token
        String token = httpRequest.getHeader(AUTHORIZATION_HEADER);

        //如果header中不存在token，则从参数中获取token
        if (token == null || "".equals(token.trim())) {
            token = httpRequest.getParameter(AUTHORIZATION_HEADER);
        }

        return token;
    }
}
