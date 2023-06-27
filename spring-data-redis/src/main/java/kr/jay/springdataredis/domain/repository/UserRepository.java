package kr.jay.springdataredis.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.jay.springdataredis.domain.entity.User;

/**
 * UserRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/06/27
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
