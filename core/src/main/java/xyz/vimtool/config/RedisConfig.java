package xyz.vimtool.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * Redis配置类
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @date    2018/9/28
 */
@Configuration
@EnableCaching
public class RedisConfig {

    /**
     * 缓存管理器
     *
     * @param factory redis连接工厂（Lettuce/Jedis），这里使用Lettuce
     * @return CacheManager
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        // todo 自定义序列化，使用fastjson序列化时，反序列化有bug，待修复
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5))
                .serializeKeysWith(
                        RedisSerializationContext
                                .SerializationPair
                                .fromSerializer(new StringRedisSerializer())
                )
                .serializeValuesWith(
                        RedisSerializationContext
                                .SerializationPair
                                .fromSerializer(new FastJsonRedisSerializer<>(Object.class))
                )
                .disableCachingNullValues();
        return RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .transactionAware()
                .build();
    }

    /**
     * redis模板操作类，覆盖默认模版类
     *
     * @param factory redis连接工厂（Lettuce/Jedis），这里使用Lettuce
     * @return RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        // 使用StringRedis序列化
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        // 使用fastjson序列化
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);

        template.setConnectionFactory(factory);

        // key的序列化采用StringRedisSerializer
        template.setKeySerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer);

        // value值的序列化采用fastJsonRedisSerializer
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * redis消息监听容器，用于添加订阅消息
     *
     * @param factory redis连接工厂（Lettuce/Jedis），这里使用Lettuce
     * @return container
     */
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory factory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        return container;
    }
}
