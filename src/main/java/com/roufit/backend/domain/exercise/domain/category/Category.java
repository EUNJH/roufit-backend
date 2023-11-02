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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private Set<Category> children = new LinkedHashSet<>();

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryLevel level;

    @Column(name = "category_order")
    private String order;

    @Builder
    public Category(Long id, Category parent,
                    Set<Category> children,
                    String title) {
        this.id = id;
        this.parent = parent;
        this.children = children;
        this.title = title;
        this.level = CategoryLevel.nextLevel(parent);
        this.order = makeOrder(parent);
    }

    public List<Long> parseOrder() {
        return Arrays.stream(order.split("\\."))
                .map(Long::valueOf)
                .toList();
    }

    private String makeOrder(Category parent) {
        if(parent == null) return null;
        if(parent.getOrder() == null) return Long.toString(parent.getId());
        return parent.getOrder() + "." + parent.getId();
    }
}
