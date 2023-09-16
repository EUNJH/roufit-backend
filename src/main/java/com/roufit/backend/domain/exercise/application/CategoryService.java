package com.roufit.backend.domain.exercise.application;

import com.roufit.backend.domain.exercise.dao.CategoryRepository;
import com.roufit.backend.domain.exercise.domain.category.Category;
import com.roufit.backend.domain.exercise.dto.category.CategoryRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public void create(CategoryRequest dto) {
        Category parent = categoryRepository.findById(dto.getParentId())
                .orElseThrow(); //TODO 부모 카테고리가 존재하지 않는다는 에러
        categoryRepository.save(dto.toEntity(parent));
    }



}
