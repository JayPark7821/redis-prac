package kr.jay.springdataredis.service;

import static kr.jay.springdataredis.config.CacheConfig.*;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import kr.jay.springdataredis.domain.entity.User;
import kr.jay.springdataredis.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;

/**
 * UserService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/06/27
 */
@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final RedisTemplate<String, User> userRedisTemplate;
	private final RedisTemplate<String, Object> objectRedisTemplate;

	public User getUser(final Long id) {
		final String key = "users:%d".formatted(id);
		final User cachedUser = (User) objectRedisTemplate.opsForValue().get(key);
		if(cachedUser != null) {
			return cachedUser;
		}
		final User user = userRepository.findById(id).orElseThrow();
		objectRedisTemplate.opsForValue().set(key, user);
		return user;
	}

	@Cacheable(cacheNames = CACHE1, key = "'user:' + #id")
	public User getUser1(final Long id) {
		return userRepository.findById(id).orElseThrow();
	}

}
