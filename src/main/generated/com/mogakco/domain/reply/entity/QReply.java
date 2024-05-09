package com.mogakco.domain.reply.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReply is a Querydsl query type for Reply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReply extends EntityPathBase<Reply> {

    private static final long serialVersionUID = 1129268269L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReply reply = new QReply("reply");

    public final com.mogakco.global.common.audit.entity.QBaseDateTimeEntity _super = new com.mogakco.global.common.audit.entity.QBaseDateTimeEntity(this);

    public final com.mogakco.domain.apply.entity.QApplyPost applyPost;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.mogakco.domain.member.entity.QMember member;

    public final ListPath<Reply, QReply> nestedReply = this.<Reply, QReply>createList("nestedReply", Reply.class, QReply.class, PathInits.DIRECT2);

    public final QReply parentReply;

    public final StringPath text = createString("text");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QReply(String variable) {
        this(Reply.class, forVariable(variable), INITS);
    }

    public QReply(Path<? extends Reply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReply(PathMetadata metadata, PathInits inits) {
        this(Reply.class, metadata, inits);
    }

    public QReply(Class<? extends Reply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.applyPost = inits.isInitialized("applyPost") ? new com.mogakco.domain.apply.entity.QApplyPost(forProperty("applyPost"), inits.get("applyPost")) : null;
        this.member = inits.isInitialized("member") ? new com.mogakco.domain.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
        this.parentReply = inits.isInitialized("parentReply") ? new QReply(forProperty("parentReply"), inits.get("parentReply")) : null;
    }

}

