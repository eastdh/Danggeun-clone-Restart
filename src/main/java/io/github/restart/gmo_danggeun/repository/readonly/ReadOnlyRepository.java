package io.github.restart.gmo_danggeun.repository.readonly;

import jakarta.persistence.Id;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface ReadOnlyRepository<T, Long> extends Repository<T, Long> {
  List<T> findAll();
  List<T> findAll(Sort sort);
  Page<T> findAll(Pageable pageable);
  Optional<T> findById(Long id);
  long count();
}
