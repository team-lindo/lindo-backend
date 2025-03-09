package team.lindo.backend.application.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPosting is a Querydsl query type for Posting
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPosting extends EntityPathBase<Posting> {

    private static final long serialVersionUID = -642457446L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPosting posting = new QPosting("posting");

    public final team.lindo.backend.application.common.entity.QBaseEntity _super = new team.lindo.backend.application.common.entity.QBaseEntity(this);

    public final ListPath<team.lindo.backend.application.social.entity.Comment, team.lindo.backend.application.social.entity.QComment> comments = this.<team.lindo.backend.application.social.entity.Comment, team.lindo.backend.application.social.entity.QComment>createList("comments", team.lindo.backend.application.social.entity.Comment.class, team.lindo.backend.application.social.entity.QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final ListPath<team.lindo.backend.application.social.entity.Like, team.lindo.backend.application.social.entity.QLike> likes = this.<team.lindo.backend.application.social.entity.Like, team.lindo.backend.application.social.entity.QLike>createList("likes", team.lindo.backend.application.social.entity.Like.class, team.lindo.backend.application.social.entity.QLike.class, PathInits.DIRECT2);

    public final SetPath<PostingProduct, QPostingProduct> postingProducts = this.<PostingProduct, QPostingProduct>createSet("postingProducts", PostingProduct.class, QPostingProduct.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public final team.lindo.backend.application.user.entity.QUser user;

    public QPosting(String variable) {
        this(Posting.class, forVariable(variable), INITS);
    }

    public QPosting(Path<? extends Posting> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPosting(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPosting(PathMetadata metadata, PathInits inits) {
        this(Posting.class, metadata, inits);
    }

    public QPosting(Class<? extends Posting> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new team.lindo.backend.application.user.entity.QUser(forProperty("user")) : null;
    }

}

