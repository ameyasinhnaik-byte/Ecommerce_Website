package com.ecommerce.ecom.service;

import com.ecommerce.ecom.model.Category;
import com.ecommerce.ecom.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
//    private List<Category> categories = new ArrayList<>();
//    private Long nextId = 1L;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Status createCategory(Category category) {
        if (category.getCategoryName() == null || category.getCategoryName().isEmpty()) {
            return new Status(HttpStatus.BAD_REQUEST, "Failed to create category");
        }

        for (Category category1 : categoryRepository.findAll()) {
            if (category.getCategoryName().equalsIgnoreCase(category1.getCategoryName())) {
                return new Status(HttpStatus.CONFLICT, "Category already exists");
            }
        }
//        category.setCategoryId(nextId++);

        categoryRepository.save(category);
        return new Status(HttpStatus.CREATED, "Category created successfully");
    }

    @Override
    public String deleteCategory(Long categoryId) {
//        List<Category> categories = categoryRepository.findAll();
//
//        Category category = categories.stream()
//                .filter(c -> c.getCategoryId().equals(categoryId))
//                .findFirst()
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
//
//        categoryRepository.delete(category);

        Optional<Category> category = categoryRepository.findById(categoryId);
        Category category1 = category
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        categoryRepository.delete(category1);

        return "Category with id: " + categoryId + " successfully deleted";
    }

    @Override
    public String updateCategory(Long categoryId, Category category) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        Category category1 = categoryOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        category1.setCategoryName(category.getCategoryName());
        categoryRepository.save(category1);
        return "Category with id: " + categoryId + " successfully updated";

//        List<Category> categories = categoryRepository.findAll();

        //approach 2 to update
//        Optional<Category> optionalCategory = categories
//                .stream()
//                .filter(c -> c.getCategoryId().equals(categoryId))
//                .findFirst();
//
//        if (optionalCategory.isPresent()) {
//            Category category1 = optionalCategory.get();
//            category1.setCategoryName(category.getCategoryName());
//            categoryRepository.save(category1);
//            return new Status(HttpStatus.OK, "Category with id: " + categoryId + " successfully updated");
//        }

        //approach 1 to update
//        for (int i = 0; i < categories.size(); i++) {
//            if (categories.get(i).getCategoryId().equals(categoryId)) {
//                categories.get(i).setCategoryName(category.getCategoryName());
//                return new Status(HttpStatus.OK, "Category with id: " + categoryId + " successfully updated");
//            }
//        }

    }
}
