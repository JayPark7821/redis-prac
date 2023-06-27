package kr.jay.jediscache;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/06/27
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
