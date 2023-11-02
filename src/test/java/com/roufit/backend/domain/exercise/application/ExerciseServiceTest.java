package com.roufit.backend.domain.exercise.application;

import com.roufit.backend.domain.exercise.dao.exercise.ExerciseRepository;
import com.roufit.backend.domain.exercise.dto.request.ExerciseRequest;
import com.roufit.backend.global.error.exception.DuplicateException;
import com.roufit.backend.global.error.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ExerciseServiceTest {
    @Mock
    private ExerciseRepository exerciseRepository;
    @Mock
    private ExerciseCategoryService exerciseCategoryService;
    @InjectMocks
    private ExerciseService exerciseService;

    @Test
    public void create_이미_동명운동_존재() throws Exception {
        //given
        given(exerciseRepository.existsByName(any()))
                .willReturn(true);

        //then
        assertThrows(DuplicateException.class
                , () -> exerciseService.create(new ExerciseRequest()));
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