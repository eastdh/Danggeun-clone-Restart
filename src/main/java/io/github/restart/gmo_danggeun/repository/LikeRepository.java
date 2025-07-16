package io.github.restart.gmo_danggeun.repository;

import io.github.restart.gmo_danggeun.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByUserId(Long userId);
}