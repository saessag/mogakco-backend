package com.mogakco.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1344064773L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final com.mogakco.global.common.audit.entity.QBaseDateTimeEntity _super = new com.mogakco.global.common.audit.entity.QBaseDateTimeEntity(this);

    public final ListPath<com.mogakco.domain.apply.entity.ApplyPost, com.mogakco.domain.apply.entity.QApplyPost> applyPosts = this.<com.mogakco.domain.apply.entity.ApplyPost, com.mogakco.domain.apply.entity.QApplyPost>createList("applyPosts", com.mogakco.domain.apply.entity.ApplyPost.class, com.mogakco.domain.apply.entity.QApplyPost.class, PathInits.DIRECT2);

    public final ListPath<com.mogakco.domain.apply.entity.ApplyRecord, com.mogakco.domain.apply.entity.QApplyRecord> applyRecords = this.<com.mogakco.domain.apply.entity.ApplyRecord, com.mogakco.domain.apply.entity.QApplyRecord>createList("applyRecords", com.mogakco.domain.apply.entity.ApplyRecord.class, com.mogakco.domain.apply.entity.QApplyRecord.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.mogakco.domain.apply.entity.InvitationRecord, com.mogakco.domain.apply.entity.QInvitationRecord> invitationRecords = this.<com.mogakco.domain.apply.entity.InvitationRecord, com.mogakco.domain.apply.entity.QInvitationRecord>createList("invitationRecords", com.mogakco.domain.apply.entity.InvitationRecord.class, com.mogakco.domain.apply.entity.QInvitationRecord.class, PathInits.DIRECT2);

    public final EnumPath<com.mogakco.domain.member.type.MemberStatus> memberStatus = createEnum("memberStatus", com.mogakco.domain.member.type.MemberStatus.class);

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final com.mogakco.domain.profile.entity.QProfile profile;

    public final ListPath<com.mogakco.domain.reply.entity.Reply, com.mogakco.domain.reply.entity.QReply> replies = this.<com.mogakco.domain.reply.entity.Reply, com.mogakco.domain.reply.entity.QReply>createList("replies", com.mogakco.domain.reply.entity.Reply.class, com.mogakco.domain.reply.entity.QReply.class, PathInits.DIRECT2);

    public final EnumPath<com.mogakco.domain.member.type.Role> role = createEnum("role", com.mogakco.domain.member.type.Role.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.profile = inits.isInitialized("profile") ? new com.mogakco.domain.profile.entity.QProfile(forProperty("profile")) : null;
    }

}

