package kr.jay.paymentservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventStreamListener implements StreamListener<String, MapRecord<String, String, String>> {

	int paymentProcessId = 0 ;
	private final StringRedisTemplate redisTemplate;

	public OrderEventStreamListener( final StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void onMessage(final MapRecord<String, String, String> message) {
		final Map<String, String> value = message.getValue();
		System.out.println(value);

		final String userId = value.get("userId");
		final String productId = value.get("productId");
		final String price = value.get("price");

		// 결제 로직 처리
		String paymentId = Integer.toString(paymentProcessId++);

		// 결제 완료 이벤트 발행
		final Map<String, String> fieldMap = new HashMap<>();
		fieldMap.put("userId", userId);
		fieldMap.put("productId", productId);
		fieldMap.put("price", price);
		fieldMap.put("paymentId", paymentId);

		System.out.println(fieldMap);

		redisTemplate.opsForStream().add("payment-events", fieldMap);
		System.out.println("Payment created " + paymentId);

	}
}
