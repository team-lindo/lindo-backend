package team.lindo.backend.application.search.service;

import org.springframework.stereotype.Component;
import team.lindo.backend.application.board.entity.Posting;
import team.lindo.backend.application.board.entity.PostingProduct;
import team.lindo.backend.application.product.entity.Product;

@Component
public class PostMatchScorer {
    public int calculateMatchScore(Posting post, String query) {
        int score = 0;
        String lowerQuery = query.toLowerCase();

        //  해시태그
        if (post.getHashtags().stream().anyMatch(tag -> tag.toLowerCase().contains(lowerQuery))) {
            score += 100;
        }

        //  포함된 제품들
        for (PostingProduct pp : post.getPostingProducts()) {
            Product product = pp.getProduct();
            String name = product.getName().toLowerCase();
            if (name.startsWith(lowerQuery)) {
                score += 100;
            } else if (name.contains(lowerQuery)) {
                score += 50;
            }
            if (product.getBrand() != null && product.getBrand().toLowerCase().contains(lowerQuery)) {
                score += 50;
            }
            if (product.getCategory() != null &&
                    product.getCategory().getName().toLowerCase().contains(lowerQuery)) {
                score += 20;
            }
        }

        return score;
    }
}

