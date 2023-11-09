package com.roufit.backend.domain.exercise.dto.request;

import com.roufit.backend.domain.exercise.domain.category.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "카테고리 요청")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryRequest {

    @Positive(message = "상위 카테고리 ID는 양수여야 합니다.")
    private Long parentId;

    @NotBlank(message = "카테고리 제목은 빈 값일 수 없습니다.")
    private String title;

    public Category toEntity(Category parent) {
        return Category.builder()
                .parent(parent)
                .title(title)
                .build();
    }
}
