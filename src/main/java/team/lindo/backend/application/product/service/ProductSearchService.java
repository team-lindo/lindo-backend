package team.lindo.backend.application.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.lindo.backend.application.product.dto.ProductSearchDto;
import team.lindo.backend.application.matcher.ProductMatchScorer;
import team.lindo.backend.application.product.repository.ProductRepository;

import java.util.AbstractMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductSearchService {
    private final ProductRepository productRepository;
    private final ProductMatchScorer matchScorer;

    public List<ProductSearchDto> search(String query) {
        return productRepository.findAll().stream()
                .map(p -> new AbstractMap.SimpleEntry<>(p, matchScorer.calculateMatchScore(p, query)))
                .filter(entry -> entry.getValue() > 0)
                .sorted((a, b) -> b.getValue() - a.getValue())
                .map(entry -> ProductSearchDto.from(entry.getKey()))
                .collect(Collectors.toList());
    }
}
