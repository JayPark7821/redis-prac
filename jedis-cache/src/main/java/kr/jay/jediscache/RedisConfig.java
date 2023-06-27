package kr.jay.jediscache;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisPool;

/**
 * RedisConfig
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/06/27
 */
@Component
public class RedisConfig {

	@Bean
	public JedisPool createJedisPool() {
		return new JedisPool("localhost", 6379);
	}
}
