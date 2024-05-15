package com.mogakco.domain.apply.entity;

import com.mogakco.domain.category.entity.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE apply_post_category SET deleted = true WHERE apply_post_category_id = ?")
@SQLRestriction("deleted = false")
public class ApplyPost_Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_post_category_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "apply_post_id")
    private ApplyPost applyPost;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Comment("삭제 여부")
    @Column(nullable = false)
    private boolean deleted;

    @Builder
    public ApplyPost_Category(ApplyPost applyPost, Category category) {
        this.applyPost = applyPost;
        this.category = category;
    }
}
