package com.roufit.backend.domain.workout.application.template;

import com.roufit.backend.domain.exercise.application.ExerciseFindService;
import com.roufit.backend.domain.workout.dao.template.SetTemplateRepository;
import com.roufit.backend.domain.workout.domain.template.InitTemplate;
import com.roufit.backend.domain.workout.domain.template.SetTemplate;
import com.roufit.backend.domain.workout.domain.template.WorkoutTemplate;
import com.roufit.backend.domain.workout.dto.request.SetTemplateRequest;
import com.roufit.backend.global.error.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SetTemplateServiceTest {
    @InjectMocks
    private SetTemplateService setTemplateService;
    @Mock
    private SetTemplateRepository setTemplateRepository;
    @Mock
    private ExerciseFindService exerciseFindService;

    @Test
    public void create_정상동작() throws Exception {
        //given
        List<SetTemplateRequest> request = List.of(
                SetTemplateRequest.builder()
                        .exerciseId(1L)
                        .goalRepetition(10)
                        .setCount(5)
                        .increaseOffset(1)
                        .restPeriod(60)
                        .build(),
                SetTemplateRequest.builder()
                        .exerciseId(2L)
                        .goalRepetition(10)
                        .setCount(5)
                        .increaseOffset(5)
                        .restPeriod(60)
                        .build()
        );
        given(setTemplateRepository.saveAll(any())).willAnswer(t -> {
            List<SetTemplate> arguments = (List<SetTemplate>) t.getArguments()[0];
            return arguments;
        });
        //when
        List<SetTemplate> setTemplates =
                setTemplateService.createSetTemplates(request, new WorkoutTemplate());
        //then
        assertThat(setTemplates.size()).isEqualTo(2);
        for (int i = 0; i < setTemplates.size(); i++) {
             assertThat(request.get(i).getGoalRepetition())
                     .isEqualTo(setTemplates.get(i).getGoalRepetition());
            assertThat(request.get(i).getIncreaseOffset())
                    .isEqualTo(setTemplates.get(i).getIncreaseOffset());
        }
    }

    @Test
    public void findById_있을때() throws Exception {
        InitTemplate initTemplate = new InitTemplate();
        //given
        given(setTemplateRepository.findById(any()))
                .willReturn(Optional.of(initTemplate.setTemplates.get(0)));
        //when
        SetTemplate setTemplate = setTemplateService.findById(1L);

        //then
        assertThat(setTemplate).isEqualTo(initTemplate.setTemplates.get(0));
    }

    @Test
    public void findById_없을때() throws Exception {
        //given
        given(setTemplateRepository.findById(any())).willReturn(Optional.ofNullable(null));

        //when & then
        assertThrows(EntityNotFoundException.class, () -> setTemplateService.findById(0L));
    }

    @Test
    public void findAllByIds_성공() throws Exception {
        //given
        InitTemplate initTemplate = new InitTemplate();
        given(setTemplateRepository.findByIdIn(any()))
                .willReturn(initTemplate.setTemplates);
        //when
        List<SetTemplate> setTemplates = setTemplateService.findAllByIds(List.of(1L, 2L));
        //then
        assertThat(setTemplates).isEqualTo(initTemplate.setTemplates);
    }

    @Test
    public void findAllByIds_ID갯수만큼_존재하지_않을때() throws Exception {
        //given
        InitTemplate initTemplate = new InitTemplate();
        given(setTemplateRepository.findByIdIn(any()))
                .willReturn(initTemplate.setTemplates);
        //when & then
        assertThrows(EntityNotFoundException.class,
                () ->setTemplateService.findAllByIds(List.of(1L, 2L, 3L)));
    }

}