package xyz.vimtool.helper;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * redis存取帮助类
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @date    2018/9/29
 */
@Component
@SuppressWarnings("unchecked")
public class RedisHelper {

    private RedisTemplate redisTemplate;

    @Inject
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 写入缓存
     *
     * @param key   存储的key
     * @param value 存储的值
     */
    public void set(String key, Object value) {
        ValueOperations operations = redisTemplate.opsForValue();
        operations.set(key, value);
    }

    /**
     * 写入缓存
     *
     * @param key     存储的key
     * @param value   存储的值
     * @param timeout 过期时间(秒)
     */
    public void set(String key, Object value, long timeout) {
        set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 写入缓存
     *
     * @param key      存储的key
     * @param value    存储的值
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     */
    public void set(String key, Object value, long timeout, TimeUnit timeUnit) {
        ValueOperations operations = redisTemplate.opsForValue();
        operations.set(key, value, timeout, timeUnit);
    }

    /**
     * 设置过期时间
     *
     * @param key    存储的key
     * @param timeOut 过期时间
     * @param timeUnit 时间单位
     * @return boolean
     */
    public boolean expire(String key, long timeOut, TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeOut, timeUnit);
    }


    /**
     * 判断缓存中是否有对应的value
     *
     * @param key 存储的key
     * @return boolean
     */
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除对应的value
     *
     * @param key 存储的key
     */
    public void delete(String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 读取缓存
     *
     * @param key 存储的key
     * @param type 存储值类型
     * @return T
     */
    public <T> T get(String key, Class<T> type) {
        ValueOperations ops = redisTemplate.opsForValue();
        Object o = ops.get(key);

        if (Objects.isNull(o)) {
            return null;
        }

        if (o instanceof JSONObject) {
            return ((JSONObject) o).toJavaObject(type);
        }

        return (T) o;
    }

    /**
     * 写入缓存
     *
     * @param key   存储的key
     * @param value 存储的值
     * @return boolean
     */
    public void hashSet(String key, String field, Object value) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put(key, field, value);
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key 存储的key
     * @return boolean
     */
    public boolean hashExists(String key, String field) {
        HashOperations operations = redisTemplate.opsForHash();
        return operations.hasKey(key, field);
    }

    /**
     * 删除对应的value
     *
     * @param key 存储的key
     * @param hashKeys 存储的hashKey
     */
    public void hashDelete(String key, String... hashKeys) {
        HashOperations operations = redisTemplate.opsForHash();
        operations.delete(key, hashKeys);
    }

    /**
     * 读取缓存
     *
     * @param key   存储的key
     * @param field 存储的hash
     * @return Object
     */
    public <T> T hashGet(String key, String field, Class<T> type) {
        Object o = redisTemplate.opsForHash().get(key, field);

        if (Objects.isNull(o)) {
            return null;
        }

        if (o instanceof JSONObject) {
            return ((JSONObject) o).toJavaObject(type);
        }

        return (T) o;
    }

    /**
     * 读取缓存中所有值
     *
     * @param key 存储的key
     * @return Map
     */
    public <T> Map<String, T> hashGetAll(String key, Class<T> type) {
        HashOperations operations = redisTemplate.opsForHash();
        Map<String, Object> entries = operations.entries(key);
        Map<String, T> result = new HashMap<>(entries.size());
        entries.forEach((k, v) -> result.put(k, ((JSONObject) v).toJavaObject(type)));
        return result;
    }

    /**
     * 发送消息
     *
     * @param channel  频道
     * @return message 消息
     */
    public void convertAndSend(String channel, Object message) {
        redisTemplate.convertAndSend(channel, message);
    }
}
