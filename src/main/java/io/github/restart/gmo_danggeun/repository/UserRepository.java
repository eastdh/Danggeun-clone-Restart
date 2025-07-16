package io.github.restart.gmo_danggeun.repository;

import io.github.restart.gmo_danggeun.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);


    boolean existsByNickname(String nickname);


    boolean existsByEmail(String email);

}