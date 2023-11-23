package com.roufit.backend.domain.workout.application.template;

import com.roufit.backend.domain.user.application.UserService;
import com.roufit.backend.domain.user.dto.SecurityUserDto;
import com.roufit.backend.domain.workout.dao.template.WorkoutTemplateRepository;
import com.roufit.backend.domain.workout.domain.template.InitTemplate;
import com.roufit.backend.domain.workout.domain.template.WorkoutTemplate;
import com.roufit.backend.domain.workout.dto.request.WorkoutTemplateRequest;
import com.roufit.backend.domain.workout.dto.response.WorkoutTemplateResponse;
import com.roufit.backend.global.error.exception.DuplicateException;
import com.roufit.backend.global.error.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class WorkoutTemplateServiceTest {

    @InjectMocks
    private WorkoutTemplateService workoutTemplateService;
    @Mock
    private WorkoutTemplateRepository workoutTemplateRepository;
    @Mock
    private UserService userService;
    @Mock
    private SetTemplateService setTemplateService;

    @Test
    public void create_성공() throws Exception {
        //given
        InitTemplate initTemplate = new InitTemplate();
        given(workoutTemplateRepository.existsByUserId(any()))
                .willReturn(false);
        WorkoutTemplateRequest request = WorkoutTemplateRequest.builder()
                .templateName("test")
                .build();
        given(setTemplateService.createSetTemplates(any(),any()))
                .willReturn(initTemplate.setTemplates);
        //when
        WorkoutTemplateResponse response = workoutTemplateService.create(request,
                SecurityUserDto.builder().id(1L).build());
        //then
        assertThat(response.getTemplateName()).isEqualTo(request.getTemplateName());
        assertThat(response.getSetTemplateResponses().size())
                .isEqualTo(initTemplate.setTemplates.size());
    }

    @Test
    public void create_이미_템플릿이_존재() throws Exception {
        //given
        given(workoutTemplateRepository.existsByUserId(any()))
                .willReturn(true);

        //when & then
        assertThrows(DuplicateException.class,
                () -> workoutTemplateService.create(
                        WorkoutTemplateRequest.builder().build(),
                        SecurityUserDto.builder().id(1L).build()
                ));

    }

    @Test
    public void findByUser_있을떄() throws Exception {
        //given
        InitTemplate initTemplate = new InitTemplate();
        given(workoutTemplateRepository.findTemplateAndSetByUserId(any()))
                .willReturn(initTemplate.workoutTemplate);

        //when
        WorkoutTemplateResponse response = workoutTemplateService
                .findByUser(SecurityUserDto.builder().build());

        // then
        assertThat(response.getTemplateName()).isEqualTo(initTemplate.workoutTemplate.getTemplateName());
        assertThat(response.getRecentPerformDate()).isEqualTo(initTemplate.workoutTemplate.getRecentPerformDate());
        assertThat(response.getSetTemplateResponses().size()).isEqualTo(2);
    }

    @Test
    public void findByUser_없을떄() throws Exception {
        //given
        given(workoutTemplateRepository.findTemplateAndSetByUserId(any()))
                .willReturn(null);

        //when & then
        assertThrows(EntityNotFoundException.class,
                () -> workoutTemplateService.findByUser(
                        SecurityUserDto.builder().id(1L).build()
                ));
    }

    @Test
    public void findById_있을떄() throws Exception {
        //given
        InitTemplate initTemplate = new InitTemplate();
        given(workoutTemplateRepository.findTemplateAndSetById(any()))
                .willReturn(initTemplate.workoutTemplate);

        //when
        WorkoutTemplate template = workoutTemplateService.findById(1L);

        // then
        assertThat(template.getTemplateName()).isEqualTo(initTemplate.workoutTemplate.getTemplateName());
        assertThat(template.getRecentPerformDate()).isEqualTo(initTemplate.workoutTemplate.getRecentPerformDate());
        assertThat(template.getSetTemplates().size()).isEqualTo(2);
    }

    @Test
    public void findById_없을때() throws Exception {
        //given
        given(workoutTemplateRepository.findTemplateAndSetById(any()))
                .willReturn(null);

        //when & then
        assertThrows(EntityNotFoundException.class,
                () -> workoutTemplateService.findById(1L));
    }

}