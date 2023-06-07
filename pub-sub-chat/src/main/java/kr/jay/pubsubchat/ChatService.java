package kr.jay.pubsubchat;

import java.util.Scanner;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

@Service
public class ChatService implements MessageListener {

	private final RedisMessageListenerContainer container;
	private final RedisTemplate<String, String> redisTemplate;

	public ChatService(final RedisMessageListenerContainer container, final RedisTemplate<String, String> redisTemplate) {
		this.container = container;
		this.redisTemplate = redisTemplate;
	}

	public void enterChatRoom(String chatRoomName) {
		container.addMessageListener(this, new ChannelTopic(chatRoomName));

		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {
			String line = in.nextLine();
			if (line.equals("q")) {
				System.out.println("Quit....");
				break;
			}

			redisTemplate.convertAndSend(chatRoomName, line);
		}

		container.removeMessageListener(this);
	}
	@Override
	public void onMessage(final Message message, final byte[] pattern) {
		System.out.println("Message received: " + message.toString());
	}
}
