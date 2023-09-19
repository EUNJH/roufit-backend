package com.roufit.backend.domain.workout.application;

import com.roufit.backend.domain.user.application.UserService;
import com.roufit.backend.domain.user.domain.User;
import com.roufit.backend.domain.user.dto.SecurityUserDto;
import com.roufit.backend.domain.workout.dao.WorkoutTemplateRepository;
import com.roufit.backend.domain.workout.domain.WorkoutTemplate;
import com.roufit.backend.domain.workout.dto.request.WorkoutTemplateRequest;
import com.roufit.backend.domain.workout.dto.response.WorkoutTemplateResponse;
import com.roufit.backend.global.error.BusinessException;
import com.roufit.backend.global.error.ErrorCode;
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
        User user = userService.findByEmail(userDto.getEmail());
        WorkoutTemplate newTemplate = request.toEntity(user);
        workoutTemplateRepository.save(newTemplate);

        setTemplateService.createSetTemplates(request.getSetTemplates(), newTemplate);
    }

    public WorkoutTemplateResponse getById(Long workoutTemplateId, SecurityUserDto userDto) {
        WorkoutTemplate workoutTemplate =
                workoutTemplateRepository.findByIdAndUserId(workoutTemplateId, userDto.getId())
                        .orElseThrow(NoSuchElementException::new);

        return workoutTemplate.toDto();
    }

    public List<WorkoutTemplateResponse> getAll(SecurityUserDto userDto) {
        List<WorkoutTemplate> workoutTemplates = workoutTemplateRepository.findByUserId(userDto.getId());
        return workoutTemplates.stream()
                .map(WorkoutTemplate::toDto)
                .toList();
    }

    public WorkoutTemplate findById(Long id) {
        return workoutTemplateRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_LAST_WORKOUT_TEMPLATE));
    }


}
