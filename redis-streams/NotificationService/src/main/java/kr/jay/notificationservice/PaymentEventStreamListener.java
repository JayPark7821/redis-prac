package kr.jay.notificationservice;

import java.util.Map;

import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventStreamListener implements StreamListener<String, MapRecord<String, String, String>> {

	@Override
	public void onMessage(final MapRecord<String, String, String> message) {
		final Map<String, String> value = message.getValue();
		System.out.println(value);

		final String userId = value.get("userId");
		final String paymentId = value.get("paymentId");

		System.out.println("payment consumed [ userId : " + userId + ", paymentId : " + paymentId + " ]");
		System.out.println("send payment notification to user " + userId);

	}
}
