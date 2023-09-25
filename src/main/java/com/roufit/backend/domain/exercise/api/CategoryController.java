package com.roufit.backend.domain.exercise.api;

import com.roufit.backend.domain.exercise.application.CategoryService;
import com.roufit.backend.domain.exercise.dto.request.CategoryRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Category", description = "카테고리 API 문서")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리 생성")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Validated CategoryRequest dto) {
        categoryService.create(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
