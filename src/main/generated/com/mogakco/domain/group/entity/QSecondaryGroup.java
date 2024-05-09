package com.mogakco.domain.group.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSecondaryGroup is a Querydsl query type for SecondaryGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSecondaryGroup extends EntityPathBase<SecondaryGroup> {

    private static final long serialVersionUID = 1002290525L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSecondaryGroup secondaryGroup = new QSecondaryGroup("secondaryGroup");

    public final com.mogakco.global.common.audit.entity.QBaseDateTimeEntity _super = new com.mogakco.global.common.audit.entity.QBaseDateTimeEntity(this);

    public final ListPath<com.mogakco.domain.apply.entity.ApplyPost, com.mogakco.domain.apply.entity.QApplyPost> applyPosts = this.<com.mogakco.domain.apply.entity.ApplyPost, com.mogakco.domain.apply.entity.QApplyPost>createList("applyPosts", com.mogakco.domain.apply.entity.ApplyPost.class, com.mogakco.domain.apply.entity.QApplyPost.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPrimaryGroup primaryGroup;

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QSecondaryGroup(String variable) {
        this(SecondaryGroup.class, forVariable(variable), INITS);
    }

    public QSecondaryGroup(Path<? extends SecondaryGroup> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSecondaryGroup(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSecondaryGroup(PathMetadata metadata, PathInits inits) {
        this(SecondaryGroup.class, metadata, inits);
    }

    public QSecondaryGroup(Class<? extends SecondaryGroup> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.primaryGroup = inits.isInitialized("primaryGroup") ? new QPrimaryGroup(forProperty("primaryGroup")) : null;
    }

}

