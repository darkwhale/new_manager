package org.zxy.new_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class NewManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewManagerApplication.class, args);
    }

}
