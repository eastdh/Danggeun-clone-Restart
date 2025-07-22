package io.github.restart.gmo_danggeun.repository;

import io.github.restart.gmo_danggeun.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
