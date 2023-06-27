package kr.jay.springdataredis.config;

import java.time.Duration;
import java.util.List;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * CacheConfig
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/06/27
 */
@EnableCaching
@Configuration
public class CacheConfig {

	public static final String CACHE1 = "cache1";
	public static final String CACHE2 = "cache2";

	@AllArgsConstructor
	@Getter
	public static class CacheProperties {
		private String name;
		private Integer ttl;
	}

	@Bean
	public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
		final BasicPolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator
			.builder()
			.allowIfSubType(Object.class)
			.build();

		final ObjectMapper objectMapper = new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.registerModule(new JavaTimeModule())
			.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL)
			.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);

		List<CacheProperties> properties = List.of(
			new CacheProperties(CACHE1, 300),
			new CacheProperties(CACHE2, 30)
		);
		return builder -> {
			properties.forEach(property -> {
				builder.withCacheConfiguration(
					property.getName(),
					RedisCacheConfiguration.defaultCacheConfig()
						.disableCachingNullValues()
						.serializeKeysWith(
							RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
						)
						.serializeValuesWith(
							RedisSerializationContext.SerializationPair.fromSerializer(
								new GenericJackson2JsonRedisSerializer(objectMapper))
						)
						.entryTtl(Duration.ofSeconds(property.getTtl()))
				);
			});
		};
	}
}
