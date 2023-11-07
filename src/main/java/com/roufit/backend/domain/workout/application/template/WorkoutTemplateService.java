package com.roufit.backend.domain.workout.application.template;

import com.roufit.backend.domain.user.application.UserService;
import com.roufit.backend.domain.user.domain.User;
import com.roufit.backend.domain.user.dto.SecurityUserDto;
import com.roufit.backend.domain.workout.dao.template.WorkoutTemplateRepository;
import com.roufit.backend.domain.workout.domain.template.WorkoutTemplate;
import com.roufit.backend.domain.workout.dto.request.WorkoutTemplateRequest;
import com.roufit.backend.domain.workout.dto.response.WorkoutTemplateResponse;
import com.roufit.backend.global.error.exception.BusinessException;
import com.roufit.backend.global.error.exception.DuplicateException;
import com.roufit.backend.global.error.exception.EntityNotFoundException;
import com.roufit.backend.global.error.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class WorkoutTemplateService {

    private final WorkoutTemplateRepository workoutTemplateRepository;
    private final UserService userService;
    private final SetTemplateService setTemplateService;

    @Transactional
    public void create(WorkoutTemplateRequest request, SecurityUserDto userDto) {
        if(workoutTemplateRepository.existsByUserId(userDto.getId())) {
            log.warn("[user id : " + userDto.getId() + "]의 운동 템플릿이 이미 존재합니다.");
            throw new DuplicateException(userDto.getEmail(), ErrorCode.USER_MORE_THAN_ONE_TEMPLATE);
        }

        WorkoutTemplate newTemplate = WorkoutTemplate.builder()
                .templateName(request.getTemplateName())
                .user(userService.getReferenceById(userDto.getId()))
                .build();
        workoutTemplateRepository.save(newTemplate);

        setTemplateService.createSetTemplates(request.getSetTemplates(), newTemplate);
    }

    public WorkoutTemplateResponse findByUser(SecurityUserDto userDto) {
        WorkoutTemplate workoutTemplate = workoutTemplateRepository
                .findTemplateAndSetByUserId(userDto.getId());
        if(workoutTemplate == null) {
            log.warn("[user id : " + userDto.getId() + "]의 운동 템플릿이 없습니다.");
            throw new EntityNotFoundException(
                    String.valueOf(userDto.getId()),
                    ErrorCode.WORKOUT_TEMPLATE_NOT_FOUND
            );
        }
        return workoutTemplate.toDto();
    }

    public WorkoutTemplate findById(final Long id) {
        WorkoutTemplate workoutTemplate = workoutTemplateRepository.findTemplateAndSetById(id);
        if(workoutTemplate == null) {
            log.warn("[id : " + id + "] 운동 템플릿이 없습니다.");
            throw new EntityNotFoundException(
                    String.valueOf(id),
                    ErrorCode.WORKOUT_TEMPLATE_NOT_FOUND
            );
        }
        return workoutTemplate;
    }
}
