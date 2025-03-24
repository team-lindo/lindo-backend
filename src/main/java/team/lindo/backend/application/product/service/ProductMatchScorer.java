package team.lindo.backend.application.product.service;

import org.springframework.stereotype.Component;
import team.lindo.backend.application.product.entity.Product;

@Component
public class ProductMatchScorer {

    public int calculateMatchScore(Product product, String query) {
        int score = 0;
        String lowerQuery = query.toLowerCase();

        if (product.getName().toLowerCase().contains(lowerQuery)) {
            score += 100;
        }
        if (product.getBrand() != null && product.getBrand().toLowerCase().contains(lowerQuery)) {
            score += 50;
        }
        if (product.getCategory() != null &&
                product.getCategory().getName().toLowerCase().contains(lowerQuery)) {
            score += 20;
        }

        return score;
    }
}
