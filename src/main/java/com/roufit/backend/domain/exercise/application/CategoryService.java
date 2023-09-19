package com.roufit.backend.domain.exercise.application;

import com.roufit.backend.domain.exercise.dao.category.CategoryRepository;
import com.roufit.backend.domain.exercise.domain.category.Category;
import com.roufit.backend.domain.exercise.dto.request.CategoryRequest;
import com.roufit.backend.global.error.exception.EntityNotFoundException;
import com.roufit.backend.global.error.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public void create(CategoryRequest dto) {
        Category parent = null;
        if(dto.getParentId() != null) {
            parent = findById(dto.getParentId());
        }
        categoryRepository.save(dto.toEntity(parent));
    }


    public List<Category> getAllById(final Long categoryId) {
        Category category = findById(categoryId);
        List<Long> ids = category.parseOrder();
        List<Category> categories = categoryRepository.findAllById(ids);

        if(ids.size() != categories.size())
            throw new EntityNotFoundException(ErrorCode.CATEGORY_PARENT_NOT_FOUND);

        categories.add(category);
        return categories;
    }

    public Category findById(final Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        id + " not found", ErrorCode.CATEGORY_ID_NOT_FOUND
                ));
    }
}
