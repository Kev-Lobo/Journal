package in.kevinlobo.journalApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    private ObjectMapper mapper = new ObjectMapper();

    public <T> T get(String key,Class<T> entityClass) {
        try{
            Object o = redisTemplate.opsForValue().get(key);
            if(o == null) {
                return null;
            }
            return mapper.readValue(o.toString(), entityClass);
        } catch(Exception e){
            log.error("Exception while fetching key {} from Redis", key, e);
            return null;
        }
    }

    public void set(String key, Object o, long ttl ) {
        try{
            String jsonValue = mapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key, jsonValue, ttl, TimeUnit.SECONDS);
        } catch(Exception e){
            log.error("Exception while setting key {} in Redis", key, e);
        }
    }
}
