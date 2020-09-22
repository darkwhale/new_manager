package org.zxy.new_manager.interceptor;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ManagerInterceptor())
                .addPathPatterns("/manager/logout",
                        "/user/getAll", "/user/findByUser",
                        "/application/*",
                        "/api/*",
                        "/authority/*",
                        "/apply/ok", "/apply/denied", "/apply/get*")
                .excludePathPatterns("/application/getAll", "/application/findByApp"
                        );

        registry.addInterceptor(new UserInterceptor())
                .addPathPatterns("/user/update", "/user/logout", "/user/userInfo",
                        "/apply/apply", "/apply/cancel", "/cancel/getByUser");
    }
}
