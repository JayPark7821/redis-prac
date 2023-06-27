package kr.jay.springdataredis;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import kr.jay.springdataredis.domain.entity.User;
import kr.jay.springdataredis.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableJpaAuditing
@SpringBootApplication
public class SpringDataRedisApplication implements ApplicationRunner {

	private final UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringDataRedisApplication.class, args);
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
