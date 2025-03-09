package team.lindo.backend.application.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostingProduct is a Querydsl query type for PostingProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostingProduct extends EntityPathBase<PostingProduct> {

    private static final long serialVersionUID = 1766490869L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostingProduct postingProduct = new QPostingProduct("postingProduct");

    public final QPostingProductId id;

    public final QPosting posting;

    public final team.lindo.backend.application.product.entity.QProduct product;

    public QPostingProduct(String variable) {
        this(PostingProduct.class, forVariable(variable), INITS);
    }

    public QPostingProduct(Path<? extends PostingProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostingProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostingProduct(PathMetadata metadata, PathInits inits) {
        this(PostingProduct.class, metadata, inits);
    }

    public QPostingProduct(Class<? extends PostingProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QPostingProductId(forProperty("id")) : null;
        this.posting = inits.isInitialized("posting") ? new QPosting(forProperty("posting"), inits.get("posting")) : null;
        this.product = inits.isInitialized("product") ? new team.lindo.backend.application.product.entity.QProduct(forProperty("product")) : null;
    }

}

