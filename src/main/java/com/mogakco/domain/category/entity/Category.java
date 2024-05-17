package com.mogakco.domain.category.entity;

import com.mogakco.domain.apply.entity.ApplyPost_Category;
import com.mogakco.global.common.audit.entity.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE category SET deleted = true WHERE category_id = ?")
@SQLRestriction("deleted = false")
public class Category extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer id;

    @Comment("분류 이름")
    @Column(nullable = false, length = 128)
    private String title;

    @Comment("삭제 여부")
    @Column(nullable = false)
    private boolean deleted;

    @Comment("대분류")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "primary_category_id")
    private Category primaryCategory;

    @Comment("소분류")
    @OneToMany(mappedBy = "primaryCategory", cascade = CascadeType.ALL)
    private List<Category> secondaryCategory = new ArrayList<>();

   @Comment("분류 깊이(depth)")
    private Integer depth;

   @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<ApplyPost_Category> applyPostCategories = new ArrayList<>();

   @Builder
    public Category(String title, boolean deleted, Category primaryCategory, List<Category> secondaryCategory, Integer depth) {
        this.title = title;
        this.deleted = deleted;
        this.primaryCategory = primaryCategory;
        this.secondaryCategory = secondaryCategory;
        this.depth = depth;
    }
}
