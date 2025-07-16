package io.github.restart.gmo_danggeun.repository.readonly;

import io.github.restart.gmo_danggeun.entity.Category;
import java.util.List;

public interface CategoryRepository extends ReadOnlyRepository<Category, Long> {
  List<Category> findAll();
}
