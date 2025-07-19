package io.github.restart.gmo_danggeun.service.trade.impl;

import io.github.restart.gmo_danggeun.entity.Category;
import io.github.restart.gmo_danggeun.repository.readonly.CategoryRepository;
import io.github.restart.gmo_danggeun.service.trade.CategoryService;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
  private final CategoryRepository categoryRepository;

  public CategoryServiceImpl(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public List<Category> findAll() {
    return categoryRepository.findAll();
  }
}
