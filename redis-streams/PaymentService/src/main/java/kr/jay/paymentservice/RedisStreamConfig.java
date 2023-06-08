package kr.jay.paymentservice;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;

@Configuration
public class RedisStreamConfig {

	private final OrderEventStreamListener orderEventStreamListener;

	public RedisStreamConfig(final OrderEventStreamListener orderEventStreamListener) {
		this.orderEventStreamListener = orderEventStreamListener;
	}

	@Bean
	public Subscription subscription(RedisConnectionFactory factory) {
		final StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> options =
			StreamMessageListenerContainer
			.StreamMessageListenerContainerOptions
			.builder()
			.pollTimeout(Duration.ofSeconds(1))
			.build();

		final StreamMessageListenerContainer<String, MapRecord<String, String, String>> listenerContainer =
			StreamMessageListenerContainer.create(factory, options);

		final Subscription subscription = listenerContainer.receiveAutoAck(
			Consumer.from("payment-service-group", "instance-1"),
			StreamOffset.create("order-events", ReadOffset.lastConsumed()),
			orderEventStreamListener
		);

		listenerContainer.start();
		return subscription;

		// XGROUP CREATE order-events payment-service-group $ MKSTREAM
		// XGROUP CREATE order-events notification-service-group $ MKSTREAM
		// XGROUP CREATE payment-events notification-service-group $ MKSTREAM
	}
}
