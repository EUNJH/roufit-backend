package com.roufit.backend.domain.exercise.dto;

import com.roufit.backend.common.ValidationTestInitializer;
import com.roufit.backend.domain.exercise.dto.request.ExerciseRequest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ExerciseRequestTest extends ValidationTestInitializer<ExerciseRequest> {

    @Test
    public void exerciseValidationTest() throws Exception {
        //given
        ExerciseRequest request = ExerciseRequest.builder()
                .name("")
                .category(-1L)
                .build();
        //when
        List<String> errorMessages = validate(request);

        //then
        assertThat(errorMessages).contains(
            "0 이상이어야 합니다", "공백일 수 없습니다", "널이어서는 안됩니다"
        );
    }
}