package org.zxy.new_manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.zxy.new_manager.utils.KeyUtil;

@Component
public class KeyConfig {

    @Bean
    public KeyUtil keyUtil() {
        return new KeyUtil();
    }
}
