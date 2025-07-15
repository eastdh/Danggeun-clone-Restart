package io.github.restart.gmo_danggeun.repository;

import io.github.restart.gmo_danggeun.entity.Like;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
