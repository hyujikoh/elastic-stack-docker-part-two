package com.hyujikoh.ShortUrl.domain.url;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Author : hyujikoh
 * CreatedAt : 2024-04-16
 * Desc :
 */

@org.springframework.stereotype.Service
public class Service {
    Map<String,String> map = new ConcurrentHashMap<>();
    public String createRandomUrl(String url) {
        String random = RandomStringUtils.random(8,33,125,true,true);
        map.put(random,url);
        return random;
    }

    public String getUrlFromKey(String key) {
        return map.get(key);
    }

    public Long getCountByUrl(String url) {
        return map.values().stream()
                .filter(s -> url.equals(s)).count();
    }
}
