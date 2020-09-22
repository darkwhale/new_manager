package org.zxy.new_manager.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
public class KeyUtil {

    private static final String KEY_PREFIX = "key_%s";

    private static final String USER_KEY_SET = "user_set";

    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    private Integer getIncr(String key) {
        RedisAtomicInteger redisAtomicInteger = new RedisAtomicInteger(key,
                Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        redisAtomicInteger.expire(12, TimeUnit.SECONDS);
        return redisAtomicInteger.getAndIncrement();
    }

    public String genUniqueKey() {
        Long current_second = System.currentTimeMillis() / 1000;
        String key = String.format(KEY_PREFIX, current_second);

        return current_second + MathUtil.fillDigest(getIncr(key));
    }

    /**
     * 返回随机字符串，不做重复检查
     * @param length 字符串长度
     * @return 随机字符串
     */
    public String genApiId(int length) {

        String randomStr = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuilder sb =new StringBuilder();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(randomStr.charAt(number));
        }

        return sb.toString();

    }
}
