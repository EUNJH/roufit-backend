package com.roufit.backend.domain.exercise.application;

import com.roufit.backend.domain.exercise.dao.exercise.ExerciseCategoryRepository;
import com.roufit.backend.domain.exercise.dao.exercise.ExerciseRepository;
import com.roufit.backend.domain.exercise.domain.CategoryBuilder;
import com.roufit.backend.domain.exercise.domain.category.CategoryLevel;
import com.roufit.backend.domain.exercise.domain.exercise.Equipment;
import com.roufit.backend.domain.exercise.domain.exercise.Exercise;
import com.roufit.backend.domain.exercise.domain.exercise.ExerciseType;
import com.roufit.backend.domain.exercise.dto.request.ExerciseRequest;
import com.roufit.backend.domain.exercise.dto.response.ExerciseResponse;
import com.roufit.backend.global.common.Status;
import com.roufit.backend.global.error.exception.DuplicateException;
import com.roufit.backend.global.error.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ExerciseServiceTest {
    @Mock
    private ExerciseRepository exerciseRepository;
    @Mock
    private ExerciseCategoryRepository exerciseCategoryRepository;
    @Mock
    private CategoryService categoryService;
    @InjectMocks
    private ExerciseService exerciseService;

    @Test
    public void create() throws Exception {
        //given
        ExerciseRequest request = ExerciseRequest.builder()
                .category(3L)
                .name("test")
                .description("test description")
                .type(ExerciseType.COUNT)
                .equipment(Equipment.NONE)
                .build();
        CategoryBuilder builder = new CategoryBuilder();
        given(exerciseRepository.existsByName(any()))
                .willReturn(false);
        given(categoryService.getAllById(any()))
                .willReturn(List.of(builder.parentCategory1, builder.parentCategory2));
        //when
        ExerciseResponse response = exerciseService.create(request);
        //then
        assertThat(response.getName()).isEqualTo(request.getName());
        assertThat(response.getDescription()).isEqualTo(request.getDescription());
        assertThat(response.getType()).isEqualTo(request.getType().name());
        assertThat(response.getEquipment()).isEqualTo(request.getEquipment().getEquipmentName());
        assertThat(response.getCategories().get(CategoryLevel.CATEGORY1.getViewName()))
                .isEqualTo(builder.parentCategory1.getTitle());
        assertThat(response.getCategories().get(CategoryLevel.CATEGORY2.getViewName()))
                .isEqualTo(builder.parentCategory2.getTitle());
    }

    @Test
    public void create_이미_동명운동_존재() throws Exception {
        //given
        given(exerciseRepository.existsByName(any()))
                .willReturn(true);

        //then
        assertThrows(DuplicateException.class,
                () -> exerciseService.create(new ExerciseRequest()));
    }

    @Test
    public void delete() throws Exception {
        //given
        given(exerciseRepository.findByName(any()))
                .willReturn(Optional.of(new Exercise()));
        //when
        Exercise test = exerciseService.delete("test");
        //then
        assertThat(test.getStatus()).isEqualTo(Status.REMOVE);
    }

    @Test
    public void delete_삭제할운동_없음() throws Exception {
        //given
        given(exerciseRepository.findByName(any()))
                .willReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class
                , () -> exerciseService.delete("push up"));
    }
}