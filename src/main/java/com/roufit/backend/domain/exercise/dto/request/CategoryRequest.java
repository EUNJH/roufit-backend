package com.roufit.backend.domain.exercise.dto.request;

import com.roufit.backend.domain.exercise.domain.category.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CategoryRequest {

    @PositiveOrZero
    private Long parentId;

    @NotBlank
    private String title;

    public Category toEntity(Category parent) {
        return Category.builder()
                .parent(parent)
                .title(title)
                .build();
    }
}
