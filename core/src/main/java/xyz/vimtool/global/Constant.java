package xyz.vimtool.global;

/**
 * 常量
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @date    2018/9/25
 */
public class Constant {

    public static class Role {

        /** 角色 */
        public static final String NAME_ADMIN = "admin";
    }

    public static class User {

        public static final String USER_ID = "user.current.id";
        public static final String USER_ROLES = "user.current.roles";
        public static final String SECRET = "io.jsonwebtoken.jjwt.0.9.1";

        /** 验证码 */
        public static final String CODE = "code";
    }
}
