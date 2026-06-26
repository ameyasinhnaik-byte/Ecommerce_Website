package com.ecommerce.ecom.service;

import com.ecommerce.ecom.exceptions.ApiException;
import com.ecommerce.ecom.exceptions.ResourceNotFoundException;
import com.ecommerce.ecom.model.Category;
import com.ecommerce.ecom.payload.CategoryDTO;
import com.ecommerce.ecom.payload.CategoryResponse;
import com.ecommerce.ecom.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service // This class should be registered as a bean and is also providing
         // business services, hence @Service is used
public class CategoryServiceImpl implements CategoryService{
//    private List<Category> categories = new ArrayList<>();
//    private Long nextId = 1L;

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public CategoryResponse getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new ApiException("Categories not added yet");
        }

        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);

        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);

        Category categoryWithSameName = categoryRepository.findByCategoryName(category.getCategoryName());
        if (categoryWithSameName != null) {
            throw new ApiException("Category with the name '" + category.getCategoryName() + "' already exists");
        }

        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        Category category1 = category
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        categoryRepository.delete(category1);

        return modelMapper.map(category1, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        Category category1 = categoryOptional
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        Category categoryWithSameName = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());
        if (categoryWithSameName != null) {
            throw new ApiException("Category with the name '" + categoryDTO.getCategoryName() + "' already exists");
        }

        category1.setCategoryName(categoryDTO.getCategoryName());
        Category savedCategory = categoryRepository.save(category1);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }
}



//@Override
//public String updateCategory(Long categoryId, Category category) {
//        List<Category> categories = categoryRepository.findAll();

    //approach 1 to update
//        for (int i = 0; i < categories.size(); i++) {
//            if (categories.get(i).getCategoryId().equals(categoryId)) {
//                categories.get(i).setCategoryName(category.getCategoryName());
//                return new Status(HttpStatus.OK, "Category with id: " + categoryId + " successfully updated");
//            }
//        }

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
//}


//@Override
//public String deleteCategory(Long categoryId) {
//        List<Category> categories = categoryRepository.findAll();
//
//        Category category = categories.stream()
//                .filter(c -> c.getCategoryId().equals(categoryId))
//                .findFirst()
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
//
//        categoryRepository.delete(category);
//}