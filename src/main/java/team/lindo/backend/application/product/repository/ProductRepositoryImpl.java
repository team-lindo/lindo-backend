package team.lindo.backend.application.product.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import team.lindo.backend.application.product.entity.Product;
import team.lindo.backend.application.product.entity.QCategory;
import team.lindo.backend.application.product.entity.QProduct;

import java.util.List;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Product> searchByQuery(String query) {
        QProduct product = QProduct.product;
        QCategory category = QCategory.category;

        BooleanBuilder builder = new BooleanBuilder();
        String lowerQuery = query.toLowerCase();

        builder.or(product.name.containsIgnoreCase(lowerQuery));
        builder.or(product.brand.containsIgnoreCase(lowerQuery));
        builder.or(product.category.name.containsIgnoreCase(lowerQuery));

        return queryFactory.selectFrom(product)
                .leftJoin(product.category, category).fetchJoin()
                .where(builder)
                .fetch();
    }
}
