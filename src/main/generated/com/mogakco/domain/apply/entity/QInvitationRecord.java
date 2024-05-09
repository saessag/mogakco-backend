package com.mogakco.domain.apply.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInvitationRecord is a Querydsl query type for InvitationRecord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInvitationRecord extends EntityPathBase<InvitationRecord> {

    private static final long serialVersionUID = -517434197L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInvitationRecord invitationRecord = new QInvitationRecord("invitationRecord");

    public final com.mogakco.global.common.audit.entity.QBaseDateTimeEntity _super = new com.mogakco.global.common.audit.entity.QBaseDateTimeEntity(this);

    public final BooleanPath agree = createBoolean("agree");

    public final QApplyPost applyPost;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.mogakco.domain.member.entity.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QInvitationRecord(String variable) {
        this(InvitationRecord.class, forVariable(variable), INITS);
    }

    public QInvitationRecord(Path<? extends InvitationRecord> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInvitationRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInvitationRecord(PathMetadata metadata, PathInits inits) {
        this(InvitationRecord.class, metadata, inits);
    }

    public QInvitationRecord(Class<? extends InvitationRecord> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.applyPost = inits.isInitialized("applyPost") ? new QApplyPost(forProperty("applyPost"), inits.get("applyPost")) : null;
        this.member = inits.isInitialized("member") ? new com.mogakco.domain.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

