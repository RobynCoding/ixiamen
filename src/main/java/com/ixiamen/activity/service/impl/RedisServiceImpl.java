package com.ixiamen.activity.service.impl;

import com.ixiamen.activity.service.IRedisService;
import com.ixiamen.activity.util.JsonUtil;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author luoyongbin
 * @since on 2018/5/11.
 */
@Service
public class RedisServiceImpl implements IRedisService {

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean set(final String key, final String value) throws Exception {
        Assert.hasText(key, "Key is not empty.");
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            connection.set(serializer.serialize(key), serializer.serialize(value));
            return true;
        });
    }

    @Override
    public void set(String key, Object value, Long expire) {
        redisTemplate.opsForValue().set(key, value);
        //设置过期时间
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    public boolean set(String key, String value, Long expire) {
        Assert.hasText(key, "Key is not empty.");
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            connection.setEx(serializer.serialize(key), expire, serializer.serialize(value));
            return true;
        });
    }

    @Override
    public String get(final String key) throws Exception {
        Assert.hasText(key, "Key is not empty.");
        return redisTemplate.execute((RedisCallback<String>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] value = connection.get(serializer.serialize(key));
            return serializer.deserialize(value);
        });
    }

    @Override
    public void del(final String key) {
        Assert.hasText(key, "Key is not empty.");

        redisTemplate.execute((RedisCallback<Long>) conn -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            return conn.del(serializer.serialize(key));
        });
    }


    @Override
    public boolean expire(final String key, long expire) {
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    public <T> boolean setList(String key, List<T> list) throws Exception {
        Assert.hasText(key, "Key is not empty.");

        String value = JsonUtil.getJsonString(list);
        return set(key, value);
    }

    @Override
    public <T> List<T> getList(String key, Class<T> clz) throws Exception {

        Assert.hasText(key, "Key is not empty.");

        String json = get(key);
        if (json != null) {
            return JsonUtil.readJson2Array(json, clz);
        }
        return null;
    }

    @Override
    public long lpush(final String key, Object obj) {
        Assert.hasText(key, "Key is not empty.");

        final String value = JsonUtil.getJsonString(obj);
        return redisTemplate.execute((RedisCallback<Long>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            return (long) connection.lPush(serializer.serialize(key), serializer.serialize(value));
        });
    }

    @Override
    public long rpush(final String key, Object obj) {
        Assert.hasText(key, "Key is not empty.");

        final String value = JsonUtil.getJsonString(obj);
        return redisTemplate.execute((RedisCallback<Long>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            return (long) connection.rPush(serializer.serialize(key), serializer.serialize(value));
        });
    }

    @Override
    public void hmset(String key, Object obj) {
        Assert.hasText(key, "Key is not empty.");

        Map<byte[], byte[]> data = JsonUtil.readJsonByteMap(JsonUtil.getJsonString(obj));
        redisTemplate.execute((RedisCallback<String>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            connection.hMSet(serializer.serialize(key), data);
            return "";
        });
    }

    @Override
    public <T> T hget(String key, Class<T> clz) {
        Assert.hasText(key, "Key is not empty.");

        return redisTemplate.execute((RedisCallback<T>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();

            Map<String, Object> result;

            Map<byte[], byte[]> data = connection.hGetAll(serializer.serialize(key));
            result = new HashMap<>();
            for (Map.Entry<byte[], byte[]> entry : data.entrySet()) {
                result.put(serializer.deserialize(entry.getKey()), serializer.deserialize(entry.getValue()));
            }

            return JsonUtil.json2Obj(JsonUtil.getJsonString(result), clz);
        });
    }

    @Override
    public <T> List<T> hmGetAll(String key, Class<T> clz) {
        Assert.hasText(key, "Key is not empty.");

        List<Map<String, Object>> dataList = new ArrayList<>();
        return redisTemplate.execute((RedisCallback<List<T>>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();

            Set<String> keysSet = redisTemplate.keys(key);
            Map<byte[], byte[]> data;
            Map<String, Object> result;
            for (String newKey : keysSet) {
                data = connection.hGetAll(serializer.serialize(newKey));
                result = new HashMap<>();
                for (Map.Entry<byte[], byte[]> entry : data.entrySet()) {
                    result.put(serializer.deserialize(entry.getKey()), serializer.deserialize(entry.getValue()));
                }
                dataList.add(result);
            }
            return JsonUtil.readJson2Array(JsonUtil.getJsonString(dataList), clz);
        });
    }

    @Override
    public String lpop(final String key) {
        Assert.hasText(key, "Key is not empty.");

        return redisTemplate.execute((RedisCallback<String>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] res = connection.lPop(serializer.serialize(key));
            return serializer.deserialize(res);
        });
    }

    @Override
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);

    }

}
