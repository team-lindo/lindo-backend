package team.lindo.backend.application.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductCategoryId is a Querydsl query type for ProductCategoryId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QProductCategoryId extends BeanPath<ProductCategoryId> {

    private static final long serialVersionUID = 1904241591L;

    public static final QProductCategoryId productCategoryId = new QProductCategoryId("productCategoryId");

    public final NumberPath<Long> categoryId = createNumber("categoryId", Long.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public QProductCategoryId(String variable) {
        super(ProductCategoryId.class, forVariable(variable));
    }

    public QProductCategoryId(Path<? extends ProductCategoryId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductCategoryId(PathMetadata metadata) {
        super(ProductCategoryId.class, metadata);
    }

}

