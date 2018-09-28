package xyz.vimtool.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.inject.Inject;

/**
 * 拦截器配置
 * WebMvcConfigurer比较常用的重写接口:
 * addCorsMappings                 解决跨域问题
 * addInterceptors                 添加拦截器
 * configureViewResolvers          配置视图解析器
 * configureContentNegotiation     配置内容裁决的一些选项
 * addViewControllers              视图跳转控制器
 * addResourceHandlers             静态资源处理
 * configureDefaultServletHandling 默认静态资源处理器
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @since   jdk1.8
 * @date    2018/8/27
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private AuthInterceptor authInterceptor;

    /**
     * 依赖注入方式：构造器注入、Setter注入、Field注入
     * 这里选用Setter注入
     */
    @Inject
    public void setAuthInterceptor(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    /**
     * 设置跨域支持
     * addMapping       允许任何url
     * allowedOrigins   允许任何域名使用
     * allowedHeaders   允许任何请求头
     * allowCredentials 允许cookie/token等认证证书
     * allowedMethods   允许的方法
     *
     * @param registry 跨域注册bean
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600);
    }

    /**
     * 添加自定义拦截器
     * addPathPatterns     用于添加拦截规则
     * excludePathPatterns 用于排除拦截
     *
     * @param registry 拦截器注册bean
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/");
    }
}
