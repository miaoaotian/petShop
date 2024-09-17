//package com.miaoaotian.sys_back.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.RedisSerializer;
//
//@Configuration
//public class RedisConfig {
//    @Bean
//    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory, ObjectMapper objectMapper) {
//        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(factory);//设置连接工厂
//
//        //设置key序列化器，要求key必须是字符串
//        redisTemplate.setKeySerializer(RedisSerializer.string());
//
//        //ObjectMapper om = objectMapper.copy();
//
//        //设置value序列化器
//        RedisSerializer<Object> valueSerializer = GenericJackson2JsonRedisSerializer
//                .builder().objectMapper(objectMapper).defaultTyping(true).build();
//        redisTemplate.setValueSerializer(valueSerializer);
//
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
//}
