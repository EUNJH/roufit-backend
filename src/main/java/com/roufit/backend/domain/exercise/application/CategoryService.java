package com.roufit.backend.domain.exercise.application;

import com.roufit.backend.domain.exercise.dao.CategoryRepository;
import com.roufit.backend.domain.exercise.domain.category.Category;
import com.roufit.backend.domain.exercise.dto.request.CategoryRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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


    public List<Category> getAllById(Long categoryId) {
        Category category = findById(categoryId);
        List<Long> ids = category.parseOrder();
        List<Category> categories = categoryRepository.findAllById(ids);

        if(ids.size() != categories.size()) throw new NoSuchElementException(); //TODO 부모 카테고리가 다 존재하지는 않음

        categories.add(category);
        return categories;
    }



    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(); //TODO 부모 카테고리가 존재하지 않는다는 에러
    }
}
