package kr.jay.redispubsub;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * RedisConfig
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/06/28
 */
@Configuration
public class RedisConfig {

	@Bean
	public MessageListenerAdapter messageListenerAdapter() {
		return new MessageListenerAdapter(new MessageListenService());
	}

	@Bean
	public RedisMessageListenerContainer redisMessageListenerContainer(
		final RedisConnectionFactory redisConnectionFactory,
		final MessageListenerAdapter listenerAdapter
	) {

		final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(redisConnectionFactory);
		container.addMessageListener(listenerAdapter, ChannelTopic.of("users:unregister"));
		return container;
	}

}
