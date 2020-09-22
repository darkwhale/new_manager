package org.zxy.new_manager.utils;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.ObjectUtils;
import org.zxy.new_manager.config.ApiConst;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class RedisUtil {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void power(String authority, Long legalTime) {
        stringRedisTemplate.opsForValue().set(authority, "0", legalTime, TimeUnit.SECONDS);
    }

    public void delete(String authority) {
        stringRedisTemplate.delete(authority);
    }

    public boolean get(String authority) {
        String result = stringRedisTemplate.opsForValue().get(authority);
        return result != null;
    }

    public Long legalTime(String authority) {
        Long expire = stringRedisTemplate.getExpire(authority);

        if (!ObjectUtils.isEmpty(expire) && expire < 0) {
            return 0L;
        }
        return expire;
    }

    public String makeKey(Object ... input) {
        List<String> inputList = Arrays.stream(input).map(Object::toString).collect(Collectors.toList());

        return String.join("_", inputList);
    }

    public boolean legal(String userId, Integer applicationId) {

        String result = stringRedisTemplate.opsForValue().get(makeKey(ApiConst.Authority_prefix, applicationId, userId));

        return result != null;
    }
}
