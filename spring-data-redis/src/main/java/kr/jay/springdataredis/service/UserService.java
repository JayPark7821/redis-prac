package kr.jay.springdataredis.service;

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
	public User getUser(final Long id) {
		final String key = "users:%d".formatted(id);
		final User cachedUser = userRedisTemplate.opsForValue().get(key);
		if(cachedUser != null) {
			return cachedUser;
		}
		final User user = userRepository.findById(id).orElseThrow();
		userRedisTemplate.opsForValue().set(key, user);
		return user;
	}

}
