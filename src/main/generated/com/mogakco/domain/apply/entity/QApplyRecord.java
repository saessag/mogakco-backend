package com.mogakco.domain.apply.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QApplyRecord is a Querydsl query type for ApplyRecord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QApplyRecord extends EntityPathBase<ApplyRecord> {

    private static final long serialVersionUID = 1643060574L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QApplyRecord applyRecord = new QApplyRecord("applyRecord");

    public final com.mogakco.global.common.audit.entity.QBaseDateTimeEntity _super = new com.mogakco.global.common.audit.entity.QBaseDateTimeEntity(this);

    public final QApplyPost applyPost;

    public final BooleanPath cancel = createBoolean("cancel");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.mogakco.domain.member.entity.QMember member;

    public final BooleanPath selected = createBoolean("selected");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QApplyRecord(String variable) {
        this(ApplyRecord.class, forVariable(variable), INITS);
    }

    public QApplyRecord(Path<? extends ApplyRecord> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QApplyRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QApplyRecord(PathMetadata metadata, PathInits inits) {
        this(ApplyRecord.class, metadata, inits);
    }

    public QApplyRecord(Class<? extends ApplyRecord> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.applyPost = inits.isInitialized("applyPost") ? new QApplyPost(forProperty("applyPost"), inits.get("applyPost")) : null;
        this.member = inits.isInitialized("member") ? new com.mogakco.domain.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

