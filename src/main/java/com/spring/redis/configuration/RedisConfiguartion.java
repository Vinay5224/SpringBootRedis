package com.spring.redis.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.spring.redis.entity.ccenc;

@Configuration
public class RedisConfiguartion {

	@Value("${redis.host}") 
	public String host;
	
	@Value("${redis.port}") 
	public int port;
	
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
	    JedisConnectionFactory jedisConFactory
	      = new JedisConnectionFactory();
	    jedisConFactory.setHostName(host);
	    jedisConFactory.setPort(port);
	    return jedisConFactory;
	}
	
	@Bean
	public RedisTemplate<Object, ccenc> redisTemplate() {
	    final RedisTemplate<Object, ccenc> template = new RedisTemplate<>();
	    template.setConnectionFactory(jedisConnectionFactory());
	    template.setKeySerializer(new StringRedisSerializer());
	    template.setHashValueSerializer(new GenericToStringSerializer<>(ccenc.class));
	    template.setValueSerializer(new Jackson2JsonRedisSerializer<>(ccenc.class));
	    return template;
	}
	
}
