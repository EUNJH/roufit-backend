package com.roufit.backend.domain.exercise.domain.category;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "category")
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private Set<Category> children = new LinkedHashSet<>();

    private String title;

    private CategoryLevel level;

    @Builder
    public Category(Category parent,
                    Set<Category> children,
                    String title) {
        this.parent = parent;
        this.children = children;
        this.title = title;
        this.level = CategoryLevel.nextLevel(parent.getLevel());
    }
}
