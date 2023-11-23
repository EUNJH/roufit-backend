package com.roufit.backend.domain.workout.application.template;

import com.roufit.backend.domain.user.application.UserService;
import com.roufit.backend.domain.user.dto.SecurityUserDto;
import com.roufit.backend.domain.workout.dao.template.WorkoutTemplateRepository;
import com.roufit.backend.domain.workout.domain.template.SetTemplate;
import com.roufit.backend.domain.workout.domain.template.WorkoutTemplate;
import com.roufit.backend.domain.workout.dto.request.WorkoutTemplateRequest;
import com.roufit.backend.domain.workout.dto.response.WorkoutTemplateResponse;
import com.roufit.backend.global.error.exception.DuplicateException;
import com.roufit.backend.global.error.exception.EntityNotFoundException;
import com.roufit.backend.global.error.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class WorkoutTemplateService {

    private final WorkoutTemplateRepository workoutTemplateRepository;
    private final UserService userService;
    private final SetTemplateService setTemplateService;

    @Transactional
    public WorkoutTemplateResponse create(WorkoutTemplateRequest request, SecurityUserDto userDto) {
        if(workoutTemplateRepository.existsByUserId(userDto.getId())) {
            log.warn("[user id : " + userDto.getId() + "]의 운동 템플릿이 이미 존재합니다.");
            throw new DuplicateException(userDto.getEmail(), ErrorCode.USER_MORE_THAN_ONE_TEMPLATE);
        }

        WorkoutTemplate newTemplate = WorkoutTemplate.builder()
                .templateName(request.getTemplateName())
                .user(userService.getReferenceById(userDto.getId()))
                .build();

        workoutTemplateRepository.save(newTemplate);
        List<SetTemplate> setTemplates = setTemplateService
                .createSetTemplates(request.getSetTemplates(), newTemplate);
        newTemplate.addSet(setTemplates);

        return newTemplate.toDto();
    }

    @Cacheable(value = "WorkoutTemplateResponse", key = "#userDto.getEmail()", cacheManager = "defaultCacheManager")
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
        WorkoutTemplateResponse response = workoutTemplate.toDto();
        cachingTemplate(response, userDto.getEmail());
        return response;
    }

    @CachePut(value = "WorkoutTemplateResponse", key = "#email", cacheManager = "defaultCacheManager")
    public void cachingTemplate(WorkoutTemplateResponse templateResponse, String email) {
        log.info("user : {} - caching template", email);
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
