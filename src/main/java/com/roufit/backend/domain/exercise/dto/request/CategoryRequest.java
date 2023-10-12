package com.roufit.backend.domain.exercise.dto.request;

import com.roufit.backend.domain.exercise.domain.category.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Schema(description = "카테고리 요청")
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
