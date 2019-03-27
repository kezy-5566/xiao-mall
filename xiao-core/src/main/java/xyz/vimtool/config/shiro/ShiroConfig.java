package xyz.vimtool.config.shiro;

import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro安全认证配置
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @date    2018/9/19
 */
@Configuration
public class ShiroConfig {

    @Bean
    public SecurityManager securityManager(AuthRealm authRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 使用jwt验证，必须取消该密码验证
        authRealm.setCredentialsMatcher(new AllowAllCredentialsMatcher());
        securityManager.setRealm(authRealm);

        // 关闭shiro自带的session
        DefaultSubjectDAO subjectDAO = (DefaultSubjectDAO) securityManager.getSubjectDAO();
        ((DefaultSessionStorageEvaluator) subjectDAO.getSessionStorageEvaluator()).setSessionStorageEnabled(false);

        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactory(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactory = new ShiroFilterFactoryBean();
        shiroFilterFactory.setSecurityManager(securityManager);
        Map<String, Filter> filters = shiroFilterFactory.getFilters();

        // anon表示不校验,code表示登录验证码校验,auth表示登录状态校验
        filters.put("auth", new AuthFilter());
        filters.put("code", new CodeFilter());

        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/sys/user/code", "anon");
        filterMap.put("/sys/user/login", "code,anon");

        filterMap.put("/sys/**", "auth");
        filterMap.put("/wx/**", "auth");
        shiroFilterFactory.setFilterChainDefinitionMap(filterMap);

        return shiroFilterFactory;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
