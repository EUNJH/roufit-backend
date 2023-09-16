package com.roufit.backend.domain.exercise.dao;

import com.roufit.backend.domain.exercise.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
