package com.roufit.backend.domain.exercise.dto.request;

import com.roufit.backend.domain.exercise.domain.category.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "카테고리 요청")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryRequest {

    @Nullable
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
