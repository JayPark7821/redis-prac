package kr.jay.springdataredis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import kr.jay.springdataredis.domain.entity.User;

/**
 * RedisConfig
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/06/27
 */
@Configuration
public class RedisConfig {

	@Bean
	public RedisTemplate<String, User> userRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		final ObjectMapper objectMapper = new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.registerModule(new JavaTimeModule())
			.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);

		RedisTemplate<String, User> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(objectMapper, User.class));
		return redisTemplate;
	}

	@Bean
	public RedisTemplate<String, Object> objectRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		final BasicPolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator
			.builder()
			.allowIfSubType(Object.class)
			.build();

		final ObjectMapper objectMapper = new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.registerModule(new JavaTimeModule())
			.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL)
			.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);

		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
		return redisTemplate;
	}
}
