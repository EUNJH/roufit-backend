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
                "운동 이름은 빈 값일 수 없습니다.",
                "운동 장비는 널 값일 수 없습니다.",
                "운동 장비는 널 값일 수 없습니다.",
                "카테고리 ID는 양수여야 합니다."
        );
    }
}