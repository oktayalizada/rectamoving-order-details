package com.rectasolutions.moving.booking.services;

import com.rectasolutions.moving.booking.entities.BookingDetail;
import com.rectasolutions.moving.booking.entities.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Value("${redisCacheKeyPrefix}")
    private String prefix;

    public void save(String username, BookingDetail bookingDetail, Services service) {
        String cacheKey = prefix + service + "_" + username;
        redisTemplate.opsForValue().set(cacheKey, bookingDetail);
    }

    public <T> T get(String username, Services service) {
        String cacheKey = prefix + service + "_" + username;
        return (T) redisTemplate.opsForValue().get(cacheKey);
    }

    public void invalidate(String username, Services service) {
        String cacheKey = prefix + service + "_" + username;
        redisTemplate.delete(cacheKey);
    }
}
