package in.kevinlobo.journalApp.config;

import in.kevinlobo.journalApp.entity.DbConfigEntity;
import in.kevinlobo.journalApp.repository.DbConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Autowired
    public DbConfigRepository dbConfigRepository;

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        DbConfigEntity dbConfigEntity = dbConfigRepository.findByKey("redis_config");

        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(dbConfigEntity.getHost());
        redisConfig.setPort(dbConfigEntity.getPort());
        redisConfig.setUsername(dbConfigEntity.getUserName());
        redisConfig.setPassword(dbConfigEntity.getPassword());

        return new LettuceConnectionFactory(redisConfig);
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory rCFactory){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(rCFactory);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }
}
