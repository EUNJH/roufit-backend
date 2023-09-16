package com.roufit.backend.domain.exercise.api;

import com.roufit.backend.domain.exercise.application.CategoryService;
import com.roufit.backend.domain.exercise.dto.category.CategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> create(CategoryRequest dto) {
        categoryService.create(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
