package org.zxy.new_manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.zxy.new_manager.utils.RedisUtil;

@Component
public class RedisConfig {

    @Bean
    public RedisUtil redisUtil() {
        return new RedisUtil();
    }
}
