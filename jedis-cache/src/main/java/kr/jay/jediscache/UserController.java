package kr.jay.jediscache;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * UserController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/06/27
 */
@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserRepository userRepository;
	private final JedisPool jedisPool;

	@GetMapping("/user/{id}/email")
	String getUserEmail(@PathVariable("id") Long id) {
		try(final Jedis jedis = jedisPool.getResource()) {
			final String key = "users:%d:email".formatted(id);
			String userEmail = jedis.get(key);
			if (userEmail != null) {
				return userEmail;
			}
			userEmail = userRepository.findById(id).orElse(User.builder().build()).getEmail();
			jedis.setex(key, 30, userEmail);
			return userEmail;
		}
	}
}
