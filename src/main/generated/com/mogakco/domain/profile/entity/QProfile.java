package com.mogakco.domain.profile.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProfile is a Querydsl query type for Profile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProfile extends EntityPathBase<Profile> {

    private static final long serialVersionUID = 122316557L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProfile profile = new QProfile("profile");

    public final com.mogakco.global.common.audit.entity.QBaseDateTimeEntity _super = new com.mogakco.global.common.audit.entity.QBaseDateTimeEntity(this);

    public final com.mogakco.domain.profile.part.QAddress address;

    public final StringPath bio = createString("bio");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath occupation = createString("occupation");

    public final StringPath profileImage = createString("profileImage");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final SetPath<String, StringPath> urls = this.<String, StringPath>createSet("urls", String.class, StringPath.class, PathInits.DIRECT2);

    public QProfile(String variable) {
        this(Profile.class, forVariable(variable), INITS);
    }

    public QProfile(Path<? extends Profile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProfile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProfile(PathMetadata metadata, PathInits inits) {
        this(Profile.class, metadata, inits);
    }

    public QProfile(Class<? extends Profile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new com.mogakco.domain.profile.part.QAddress(forProperty("address")) : null;
    }

}

