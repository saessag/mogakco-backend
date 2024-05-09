package com.mogakco.domain.group.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPrimaryGroup is a Querydsl query type for PrimaryGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPrimaryGroup extends EntityPathBase<PrimaryGroup> {

    private static final long serialVersionUID = 1878138383L;

    public static final QPrimaryGroup primaryGroup = new QPrimaryGroup("primaryGroup");

    public final com.mogakco.global.common.audit.entity.QBaseDateTimeEntity _super = new com.mogakco.global.common.audit.entity.QBaseDateTimeEntity(this);

    public final ListPath<com.mogakco.domain.apply.entity.ApplyPost, com.mogakco.domain.apply.entity.QApplyPost> applyPosts = this.<com.mogakco.domain.apply.entity.ApplyPost, com.mogakco.domain.apply.entity.QApplyPost>createList("applyPosts", com.mogakco.domain.apply.entity.ApplyPost.class, com.mogakco.domain.apply.entity.QApplyPost.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final SetPath<SecondaryGroup, QSecondaryGroup> secondaryGroups = this.<SecondaryGroup, QSecondaryGroup>createSet("secondaryGroups", SecondaryGroup.class, QSecondaryGroup.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPrimaryGroup(String variable) {
        super(PrimaryGroup.class, forVariable(variable));
    }

    public QPrimaryGroup(Path<? extends PrimaryGroup> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPrimaryGroup(PathMetadata metadata) {
        super(PrimaryGroup.class, metadata);
    }

}

