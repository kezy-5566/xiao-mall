package xyz.vimtool.helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.vimtool.global.Constant;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static xyz.vimtool.global.Constant.User.*;

/**
 * json web tokens
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @date    2018/9/20
 */
@Slf4j
@Component
public class JwtHelper {

    @Inject
    private RedisHelper redisHelper;

    /**
     * 根据用户id生成令牌
     */
    public String buildToken(Integer userId, Set<String> roles) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put(USER_ID, userId);
        claims.put(USER_ROLES, roles);

        String key = getKey(userId);
        String token = Jwts.builder()
                .setClaims(claims)
                .setId(key)
                .setSubject(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        redisHelper.set(key, token, 7, TimeUnit.DAYS);
        return token;
    }

    /**
     * 校验令牌，获取数据声明
     */
    public Claims verifyToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();

            if (token.equals(redisHelper.get(claims.getId(), String.class))) {
                // 刷新token有效时长
                redisHelper.set(claims.getId(), token, 7, TimeUnit.DAYS);
                return claims;
            }
        } catch (Exception e) {
            log.error("verify token failed," + e.getMessage());
        }
        return null;
    }

    /**
     * 删除令牌
     */
    public void deleteToken(Integer userId) {
        redisHelper.delete(getKey(userId));
    }

    private String getKey(Integer userId) {
        return Constant.Redis.TOKEN_KEY + userId;
    }
}
