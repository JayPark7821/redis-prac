package kr.jay.redispubsub;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * MessageListenService
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/06/28
 */
@Slf4j
@Service
public class MessageListenService implements MessageListener {

	@Override
	public void onMessage(final Message message, final byte[] pattern) {
		log.info("Message received: {}, Channel: {}", new String(message.getChannel()), new String(message.getBody()));
	}
}
