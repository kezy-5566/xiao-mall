package xyz.vimtool.config.shiro;

import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;
import xyz.vimtool.global.Constant;
import xyz.vimtool.helper.JwtHelper;
import xyz.vimtool.service.RoleService;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 登录、权限校验
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @date    2018/9/20
 */
@Component
public class AuthRealm extends AuthorizingRealm {

    @Inject
    private RoleService roleService;

    /**
     * 认证(验证登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        Claims claims = JwtHelper.verifyToken(String.valueOf(token.getPrincipal()));

        // 返回登录失败
        if (claims == null) {
            throw new RuntimeException("登录认证失败");
        }
        return new SimpleAuthenticationInfo(claims, claims.getId(), claims.getId());
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    @SuppressWarnings("unchecked")
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Claims claims = (Claims) principalCollection.getPrimaryPrincipal();
        Set<String> roles = new HashSet<>((List<String>) claims.get(Constant.User.USER_ROLES));

        // 权限校验，将角色/权限放入info
        if (!roles.isEmpty()) {
            info.setRoles(roles);
            info.setStringPermissions(roleService.listPermissions(roles));
        }
        return info;
    }
}
