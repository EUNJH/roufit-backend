package com.roufit.backend.domain.exercise.domain;

import com.roufit.backend.domain.exercise.domain.category.Category;
import com.roufit.backend.domain.exercise.domain.category.CategoryLevel;
import com.roufit.backend.domain.exercise.dto.request.CategoryRequest;
import com.roufit.backend.domain.exercise.exception.NotExistSubcategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CategoryTest {

    CategoryBuilder builder;

    @BeforeEach
    public void init() {
        builder = new CategoryBuilder();
    }

    @Test
    public void category_build_대분류() {
        //given
        CategoryRequest request = new CategoryRequest(null, "상체");

        //when
        Category target = request.toEntity(null);

        //then
        assertThat(target.getLevel()).isEqualTo(CategoryLevel.CATEGORY1);
        assertThat(target.getOrder()).isEqualTo(null);
    }

    @Test
    public void category_build_중분류() {
        //given
        CategoryRequest request = new CategoryRequest(null, "상체");

        //when
        Category target = request.toEntity(builder.parentCategory1);

        //then
        assertThat(target.getLevel()).isEqualTo(CategoryLevel.CATEGORY2);
        assertThat(target.getOrder()).isEqualTo("1");
    }

    @Test
    public void category_build_소분류() {
        //given

        CategoryRequest request = new CategoryRequest(null, "광배근");

        //when
        Category target = request.toEntity(builder.parentCategory2);

        //then
        assertThat(target.getLevel()).isEqualTo(CategoryLevel.CATEGORY3);
        assertThat(target.getOrder()).isEqualTo("1.2");
    }

    @Test
    public void category_build_부모가_최하위카테고리() {
        //given
        CategoryRequest request = new CategoryRequest(3L, "광배근");

        //when

        //then
        assertThrows(NotExistSubcategory.class,
                () -> request.toEntity(builder.parentCategory3));
    }

    @Test
    public void category_상위순서_id_파싱() throws Exception {
        //given
        List<Long> ids = List.of(1L, 2L);

        //when
        List<Long> targets = builder.parentCategory3.parseOrder();

        //then
        assertThat(ids).isEqualTo(targets);
    }
}