package com.ecommerce.ecom.service;

import com.ecommerce.ecom.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
    private List<Category> categories = new ArrayList<>();
    private Long nextId = 1L;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public Status createCategory(Category category) {
        if (category.getCategoryName() == null || category.getCategoryName().isEmpty()) {
            return new Status(HttpStatus.BAD_REQUEST, "Failed to create category");
        }

        for (Category category1 : categories) {
            if (category.getCategoryName().equalsIgnoreCase(category1.getCategoryName())) {
                return new Status(HttpStatus.CONFLICT, "Category already exists");
            }
        }
        category.setCategoryId(nextId++);

        categories.add(category);
        return new Status(HttpStatus.CREATED, "Category created successfully");
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categories.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        categories.remove(category);

        return "Category with id: " + categoryId + " successfully deleted";
    }

    @Override
    public Status updateCategory(Long categoryId, Category category) {

        Optional<Category> optionalCategory = categories
                .stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst();

        if (optionalCategory.isPresent()) {
            Category category1 = optionalCategory.get();
            category1.setCategoryName(category.getCategoryName());
            return new Status(HttpStatus.OK, "Category with id: " + categoryId + " successfully updated");
        }

//        for (int i = 0; i < categories.size(); i++) {
//            if (categories.get(i).getCategoryId().equals(categoryId)) {
//                categories.get(i).setCategoryName(category.getCategoryName());
//                return new Status(HttpStatus.OK, "Category with id: " + categoryId + " successfully updated");
//            }
//        }

        return new Status(HttpStatus.NOT_FOUND, "Category not found");

    }
}
