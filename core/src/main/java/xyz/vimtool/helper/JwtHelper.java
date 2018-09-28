package xyz.vimtool.helper;

import io.jsonwebtoken.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static xyz.vimtool.global.Constant.User.*;

/**
 * json web tokens
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @date    2018/9/20
 */
public class JwtHelper {

    /**
     * 根据用户id生成令牌
     */
    public static String buildToken(Integer userId, Set<String> roles) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put(USER_ID, userId);
        claims.put(USER_ROLES, roles);
        return buildToken(claims);
    }

    /**
     * 根据数据声明生成令牌
     */
    public static String buildToken(Map<String, Object> claims) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder = jwtBuilder.setClaims(claims);

        return jwtBuilder
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    /**
     * 校验令牌，获取数据声明
     */
    public static Claims verifyToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }
}
