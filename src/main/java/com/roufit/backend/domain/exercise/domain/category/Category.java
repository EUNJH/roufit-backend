package com.roufit.backend.domain.exercise.domain.category;

import com.roufit.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "category")
public class Category extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private Set<Category> children = new LinkedHashSet<>();

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private CategoryLevel level;

    private String order;

    @Builder
    public Category(Category parent,
                    Set<Category> children,
                    String title) {
        this.parent = parent;
        this.children = children;
        this.title = title;
        this.level = CategoryLevel.nextLevel(parent.getLevel());
        this.order = makeOrder(parent);
    }

    public List<Long> parseOrder() {
        return Arrays.stream(order.split("\\."))
                .map(Long::valueOf)
                .toList();
    }

    private String makeOrder(Category parent) {
        return parent.getOrder() + "." + parent.getId();
    }
}
