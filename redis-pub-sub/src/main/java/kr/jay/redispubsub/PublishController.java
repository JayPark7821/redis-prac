package kr.jay.redispubsub;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * PublishController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/06/28
 */
@RequiredArgsConstructor
@RestController
public class PublishController {

	private final RedisTemplate<String, String> redisTemplate;

	@PostMapping("/users/deregister")
	void publishDeregisterEvent() {
		redisTemplate.convertAndSend("users:unregister", "1");
	}
}
