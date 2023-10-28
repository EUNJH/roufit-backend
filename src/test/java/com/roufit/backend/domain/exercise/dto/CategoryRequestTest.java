package com.roufit.backend.domain.exercise.dto;

import com.roufit.backend.common.ValidationTestInitializer;
import com.roufit.backend.domain.exercise.dto.request.CategoryRequest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryRequestTest extends ValidationTestInitializer<CategoryRequest> {

    @Test
    public void categoryValidationTest() throws Exception {
        //given
        CategoryRequest request = new CategoryRequest(-1L, "");

        //when
        List<String> errorMessages = validate(request);

        //then
        assertThat(errorMessages).contains("0 이상이어야 합니다", "공백일 수 없습니다");
    }

}