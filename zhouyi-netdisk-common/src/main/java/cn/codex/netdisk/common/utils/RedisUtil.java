package cn.codex.netdisk.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author codex
 * @since 2021-02-09
 */
@Component
@SuppressWarnings(value = {"unchecked", "rawtypes"})
public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 缓存Integer、String、实体类等
     *
     * @param key   键
     * @param value 值
     */
    public <T> void setObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存Integer、String、实体类等
     *
     * @param key      键
     * @param value    值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setObject(final String key, final T value, final long timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return true 设置成功；false 设置失败
     */
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key      Redis键
     * @param timeout  超时时间
     * @param timeUnit 时间单位
     * @return true 设置成功；false 设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit timeUnit) {
        try {
            return redisTemplate.expire(key, timeout, timeUnit);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key获取缓存的对象
     *
     * @param key 键
     * @return 缓存的数据
     */
    public <T> T getObject(final String key) {
        ValueOperations<String, T> value = redisTemplate.opsForValue();
        return value.get(key);
    }

    /**
     * 根据key删除缓存对象
     *
     * @param key 键
     * @return true 删除成功， false 删除失败
     */
    public boolean deleteObject(final String key) {
        try {
            return redisTemplate.delete(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return 删除行数
     */
    public long deleteObject(final Collection collection) {
        try {
            return redisTemplate.delete(collection);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
