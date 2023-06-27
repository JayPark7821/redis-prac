package kr.jay.jediscache;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SpringBootApplication
public class JedisCacheApplication implements ApplicationRunner {

	private final UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(JedisCacheApplication.class, args);
	}

	@Override
	public void run(final ApplicationArguments args) throws Exception {
		userRepository.save(User.builder().name("tom").email("tom@gmail.com").build());
		userRepository.save(User.builder().name("jay").email("jay@gmail.com").build());
		userRepository.save(User.builder().name("kay").email("kay@gmail.com").build());
		userRepository.save(User.builder().name("bob").email("bob@gmail.com").build());
		userRepository.save(User.builder().name("ryan").email("ryan@gmail.com").build());
	}
}
