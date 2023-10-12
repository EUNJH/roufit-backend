package com.roufit.backend.domain.workout.dao.template;

import com.roufit.backend.domain.workout.domain.template.SetTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SetTemplateRepository extends JpaRepository<SetTemplate, Long> {

    List<SetTemplate> findByIdIn(List<Long> id);
}
