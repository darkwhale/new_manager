package org.zxy.new_manager.utils;

import org.springframework.web.client.RestTemplate;

public class HttpUtil  {

    public static Object get(String url) {
        RestTemplate restTemplate = new RestTemplate();

        Object response = restTemplate.getForEntity(url, Object.class);

        return response;
    }
}
