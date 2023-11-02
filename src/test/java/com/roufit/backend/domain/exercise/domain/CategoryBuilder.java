package com.roufit.backend.domain.exercise.domain;

import com.roufit.backend.domain.exercise.domain.category.Category;


public class CategoryBuilder {

    public Category parentCategory1;
    public Category parentCategory2;
    public Category parentCategory3;

    public CategoryBuilder() {
        parentCategory1 = Category.builder()
                .id(1L)
                .parent(null)
                .title("상체")
                .build();
        parentCategory2 = Category.builder()
                .id(2L)
                .parent(parentCategory1)
                .title("등")
                .build();
        parentCategory3 = Category.builder()
                .id(2L)
                .parent(parentCategory2)
                .title("등")
                .build();
    }
}
