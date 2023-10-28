package com.roufit.backend.common;

import com.roufit.backend.domain.exercise.dto.request.CategoryRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;

import java.util.List;
import java.util.Set;

public abstract class ValidationTestInitializer<T> {

    protected static ValidatorFactory factory;
    protected static Validator validator;

    @BeforeAll
    protected static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    protected List<String> validate(T request) {
        Set<ConstraintViolation<T>> validate = validator.validate(request);
        return extractMessage(validate);
    }

    private List<String> extractMessage(Set<ConstraintViolation<T>> validate) {
        return validate.stream()
                .map(ConstraintViolation::getMessage)
                .toList();
    }
}
