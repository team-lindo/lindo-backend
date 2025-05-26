package team.lindo.backend.application.search.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.lindo.backend.application.board.dto.PostDto;
import team.lindo.backend.application.board.entity.Posting;
import team.lindo.backend.application.board.entity.PostingProduct;
import team.lindo.backend.application.board.repository.posting.PostingRepository;
import team.lindo.backend.application.product.dto.ProductSummaryDto;
import team.lindo.backend.application.product.entity.Product;
import team.lindo.backend.application.search.dto.SearchResponseDto;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SearchService {

    private final PostingRepository postingRepository;
    private final PostMatchScorer matchScorer;
    @Transactional(readOnly = true)
    public SearchResponseDto search(String keyword) {
        List<Posting> matchedPosts = postingRepository.findAll().stream()
                .map(post -> new AbstractMap.SimpleEntry<>(post, matchScorer.calculateMatchScore(post, keyword)))
                .filter(entry -> entry.getValue() > 0)
                .sorted((a, b) -> b.getValue() - a.getValue())  // 점수 높은 순
                .map(Map.Entry::getKey)
                .limit(20)
                .toList();

        Set<Product> relatedProducts = matchedPosts.stream()
                .flatMap(post -> post.getPostingProducts().stream())
                .map(PostingProduct::getProduct)
                .collect(Collectors.toSet());

        List<PostDto> posts = matchedPosts.stream()
                .map(PostDto::new)
                .toList();

        List<ProductSummaryDto> products = relatedProducts.stream()
                .map(ProductSummaryDto::new)
                .toList();

        return new SearchResponseDto(products, posts);
    }
}
