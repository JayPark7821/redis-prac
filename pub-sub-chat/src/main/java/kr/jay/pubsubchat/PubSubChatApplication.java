package kr.jay.pubsubchat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PubSubChatApplication implements CommandLineRunner {

	@Autowired
	ChatService chatService;

	public static void main(String[] args) {
		SpringApplication.run(PubSubChatApplication.class, args);
	}

	@Override
	public void run(final String... args) throws Exception {
		System.out.println("Application started.....");
		chatService.enterChatRoom("chat1");
	}
}
