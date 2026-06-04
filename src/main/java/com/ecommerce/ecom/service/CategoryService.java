package com.ecommerce.ecom.service;

import com.ecommerce.ecom.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Status createCategory(Category category);
    String deleteCategory(Long categoryId);
    Status updateCategory(Long categoryId, Category category);
}
