package com.mogakco.domain.apply.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QApplyPost is a Querydsl query type for ApplyPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QApplyPost extends EntityPathBase<ApplyPost> {

    private static final long serialVersionUID = -25155347L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QApplyPost applyPost = new QApplyPost("applyPost");

    public final com.mogakco.global.common.audit.entity.QBaseDateTimeEntity _super = new com.mogakco.global.common.audit.entity.QBaseDateTimeEntity(this);

    public final DateTimePath<java.time.LocalDateTime> applyBeginDatetime = createDateTime("applyBeginDatetime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> applyEndDateTime = createDateTime("applyEndDateTime", java.time.LocalDateTime.class);

    public final ListPath<ApplyRecord, QApplyRecord> applyRecords = this.<ApplyRecord, QApplyRecord>createList("applyRecords", ApplyRecord.class, QApplyRecord.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<InvitationRecord, QInvitationRecord> invitationRecords = this.<InvitationRecord, QInvitationRecord>createList("invitationRecords", InvitationRecord.class, QInvitationRecord.class, PathInits.DIRECT2);

    public final com.mogakco.domain.member.entity.QMember member;

    public final com.mogakco.domain.group.entity.QPrimaryGroup primaryGroup;

    public final DateTimePath<java.time.LocalDateTime> projectBeginDatetime = createDateTime("projectBeginDatetime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> projectEndDatetime = createDateTime("projectEndDatetime", java.time.LocalDateTime.class);

    public final StringPath projectUrl = createString("projectUrl");

    public final ListPath<com.mogakco.domain.reply.entity.Reply, com.mogakco.domain.reply.entity.QReply> replies = this.<com.mogakco.domain.reply.entity.Reply, com.mogakco.domain.reply.entity.QReply>createList("replies", com.mogakco.domain.reply.entity.Reply.class, com.mogakco.domain.reply.entity.QReply.class, PathInits.DIRECT2);

    public final com.mogakco.domain.group.entity.QSecondaryGroup secondaryGroup;

    public final EnumPath<com.mogakco.domain.apply.type.SelectMethod> selectMethod = createEnum("selectMethod", com.mogakco.domain.apply.type.SelectMethod.class);

    public final EnumPath<com.mogakco.domain.apply.type.Status> status = createEnum("status", com.mogakco.domain.apply.type.Status.class);

    public final StringPath text = createString("text");

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final BooleanPath visible = createBoolean("visible");

    public QApplyPost(String variable) {
        this(ApplyPost.class, forVariable(variable), INITS);
    }

    public QApplyPost(Path<? extends ApplyPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QApplyPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QApplyPost(PathMetadata metadata, PathInits inits) {
        this(ApplyPost.class, metadata, inits);
    }

    public QApplyPost(Class<? extends ApplyPost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.mogakco.domain.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
        this.primaryGroup = inits.isInitialized("primaryGroup") ? new com.mogakco.domain.group.entity.QPrimaryGroup(forProperty("primaryGroup")) : null;
        this.secondaryGroup = inits.isInitialized("secondaryGroup") ? new com.mogakco.domain.group.entity.QSecondaryGroup(forProperty("secondaryGroup"), inits.get("secondaryGroup")) : null;
    }

}

