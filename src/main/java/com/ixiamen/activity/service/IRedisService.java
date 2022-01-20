package com.ixiamen.activity.service;

import java.util.List;

/**
 * @author luoyongbin
 * @since on 2018/5/11.
 */
public interface IRedisService {
    boolean set(String key, String value) throws Exception;

    /**
     * 向Redis中存放字符串[并设置过期时间]
     *
     * @param expire 单位s
     */
    void set(String key, Object value, Long expire);

    /**
     * 向Redis中存放字符串[并设置过期时间]
     *
     * @param expire 单位s
     */
    boolean set(String key, String value, Long expire);

    String get(String key) throws Exception;

    boolean expire(String key, long expire) throws Exception;

    <T> boolean setList(String key, List<T> list) throws Exception;

    <T> List<T> getList(String key, Class<T> clz) throws Exception;

    long lpush(String key, Object obj) throws Exception;

    long rpush(String key, Object obj) throws Exception;

    void hmset(String key, Object obj) throws Exception;

    <T> T hget(String key, Class<T> clz) throws Exception;


    void del(String key) throws Exception;

    <T> List<T> hmGetAll(String key, Class<T> clz) throws Exception;

    String lpop(String key) throws Exception;


    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    long getExpire(String key);
}
