package kr.jay.springdataredis.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import kr.jay.springdataredis.domain.entity.User;
import kr.jay.springdataredis.service.UserService;
import lombok.RequiredArgsConstructor;

/**
 * UserController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/06/27
 */

@RequiredArgsConstructor
@RestController
public class UserController {
	private final UserService userService;

	@GetMapping("/users/{id}")
	User getUser(@PathVariable("id") Long id) {
		return userService.getUser(id);
	}
}
