package com.ecommerce.ecom.controller;

import com.ecommerce.ecom.config.AppConstants;
import com.ecommerce.ecom.model.Category;
import com.ecommerce.ecom.payload.CategoryDTO;
import com.ecommerce.ecom.payload.CategoryResponse;
import com.ecommerce.ecom.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(value = "page", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = AppConstants.SORT_ORDER) String sortOrder
    ) {
        return new ResponseEntity<>(categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder),
                HttpStatus.OK);
    }

    @PostMapping("/admin/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<>(categoryService.createCategory(categoryDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {
        return new ResponseEntity<>(categoryService.deleteCategory(categoryId), HttpStatus.OK);
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long categoryId, @Valid @RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<>(categoryService.updateCategory(categoryId, categoryDTO), HttpStatus.OK);
    }
}




//    @DeleteMapping("/admin/categories/{categoryId}")
//    @RequestMapping(value = "/admin/categories/{categoryId}", method = RequestMethod.DELETE)  --> you can use this instead of above one as well
//    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
//        try {
//            String status = categoryService.deleteCategory(categoryId);
//            return new ResponseEntity<>(status, HttpStatus.OK);
//            return ResponseEntity.ok(status);
//            return ResponseEntity.status(HttpStatus.OK).body(status);
//        } catch (ResponseStatusException e) {
//            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
//            /* if you did e.getMessage() then you get the following as the response
//             * "404 NOT_FOUND Resource not found"
//             * if you do e.getReason() then you get the following as the response
//             * "Resource not found"
//             */
//        }
//    }




//    @PutMapping("/admin/categories/{categoryId}")
//    @RequestMapping(value = "/admin/categories/{categoryId}", method = RequestMethod.PUT)
//    public ResponseEntity<String> updateCategory(@PathVariable Long categoryId, @Valid @RequestBody Category category) {
//        try {
//            return new ResponseEntity<>((categoryService.updateCategory(categoryId, category)), HttpStatus.OK);
//        } catch(ResponseStatusException e) {
//            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
//        }

//        return new ResponseEntity<>(categoryService.updateCategory(categoryId, category), HttpStatus.OK);
//    }
