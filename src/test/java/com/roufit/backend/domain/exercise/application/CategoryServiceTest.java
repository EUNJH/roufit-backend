package com.roufit.backend.domain.exercise.application;

import com.roufit.backend.domain.exercise.dao.category.CategoryRepository;
import com.roufit.backend.domain.exercise.domain.CategoryBuilder;
import com.roufit.backend.domain.exercise.domain.category.Category;
import com.roufit.backend.global.error.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    CategoryBuilder builder;

    @BeforeEach
    public void init() {
        builder = new CategoryBuilder();
    }

    @Test
    public void 상위_카테고리_모두조회() throws Exception {
        //given
        given(categoryRepository.findAllById(any()))
                .willReturn(new ArrayList<>(List.of(builder.parentCategory1, builder.parentCategory2)));
        given(categoryRepository.findById(any()))
                .willReturn(Optional.ofNullable(builder.parentCategory3));

        //when
        List<Category> categories = categoryService.getAllById(3L);

        //then
        assertThat(categories).isEqualTo(
                List.of(builder.parentCategory1,
                        builder.parentCategory2,
                        builder.parentCategory3)
        );
    }

    @Test
    public void 상위_카테고리_모두조회_갯수다름() throws Exception {
        //given
        given(categoryRepository.findAllById(any()))
                .willReturn(new ArrayList<>(List.of(builder.parentCategory1)));
        given(categoryRepository.findById(any()))
                .willReturn(Optional.ofNullable(builder.parentCategory3));

        //then
        assertThrows(EntityNotFoundException.class
                , () -> categoryService.getAllById(3L));
    }

    @Test
    public void 카테고리_ID조회_없을때() throws Exception {
        //given
        given(categoryRepository.findById(any())).willReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class,
                () -> categoryService.findById(1L));
    }

}