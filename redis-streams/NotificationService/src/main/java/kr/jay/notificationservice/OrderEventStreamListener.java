package kr.jay.notificationservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventStreamListener implements StreamListener<String, MapRecord<String, String, String>> {

	@Override
	public void onMessage(final MapRecord<String, String, String> message) {
		final Map<String, String> value = message.getValue();
		System.out.println(value);

		final String userId = value.get("userId");
		final String productId = value.get("productId");

		System.out.println("order consumed [ userId : " + userId + ", productId : " + productId + " ]");
		System.out.println("send notification to user " + userId);

	}
}
