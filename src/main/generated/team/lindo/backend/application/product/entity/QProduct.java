package team.lindo.backend.application.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = -1195446690L;

    public static final QProduct product = new QProduct("product");

    public final StringPath brand = createString("brand");

    public final StringPath color = createString("color");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final StringPath name = createString("name");

    public final SetPath<team.lindo.backend.application.board.entity.PostingProduct, team.lindo.backend.application.board.entity.QPostingProduct> postingProducts = this.<team.lindo.backend.application.board.entity.PostingProduct, team.lindo.backend.application.board.entity.QPostingProduct>createSet("postingProducts", team.lindo.backend.application.board.entity.PostingProduct.class, team.lindo.backend.application.board.entity.QPostingProduct.class, PathInits.DIRECT2);

    public final NumberPath<Double> price = createNumber("price", Double.class);

    public final SetPath<ProductCategory, QProductCategory> productCategories = this.<ProductCategory, QProductCategory>createSet("productCategories", ProductCategory.class, QProductCategory.class, PathInits.DIRECT2);

    public final StringPath size = createString("size");

    public QProduct(String variable) {
        super(Product.class, forVariable(variable));
    }

    public QProduct(Path<? extends Product> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProduct(PathMetadata metadata) {
        super(Product.class, metadata);
    }

}

