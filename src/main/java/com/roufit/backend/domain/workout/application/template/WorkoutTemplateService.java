package com.roufit.backend.domain.workout.application.template;

import com.roufit.backend.domain.user.application.UserService;
import com.roufit.backend.domain.user.domain.User;
import com.roufit.backend.domain.user.dto.SecurityUserDto;
import com.roufit.backend.domain.workout.dao.template.WorkoutTemplateRepository;
import com.roufit.backend.domain.workout.domain.template.WorkoutTemplate;
import com.roufit.backend.domain.workout.dto.request.WorkoutTemplateRequest;
import com.roufit.backend.domain.workout.dto.response.WorkoutTemplateResponse;
import com.roufit.backend.global.error.exception.BusinessException;
import com.roufit.backend.global.error.exception.EntityNotFoundException;
import com.roufit.backend.global.error.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class WorkoutTemplateService {

    private final WorkoutTemplateRepository workoutTemplateRepository;
    private final UserService userService;
    private final SetTemplateService setTemplateService;

    @Transactional
    public void create(WorkoutTemplateRequest request, SecurityUserDto userDto) {
        User user = userService.getReferenceById(userDto.getId());
        WorkoutTemplate newTemplate = WorkoutTemplate.builder()
                .templateName(request.getTemplateName())
                .user(user)
                .build();
        workoutTemplateRepository.save(newTemplate);

        setTemplateService.createSetTemplates(request.getSetTemplates(), newTemplate);
    }

    public WorkoutTemplateResponse findByUser(SecurityUserDto userDto) {
        WorkoutTemplate workoutTemplate = workoutTemplateRepository
                .findByUserId(userDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.valueOf(userDto.getId()),
                        ErrorCode.WORKOUT_TEMPLATE_NOT_FOUND
                ));

        return workoutTemplate.toDto();
    }

    public WorkoutTemplate findById(final Long id) {
        return workoutTemplateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.valueOf(id),
                        ErrorCode.WORKOUT_TEMPLATE_NOT_FOUND
                        )
                );
    }




}
