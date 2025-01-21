package team.lindo.backend.application.board.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.lindo.backend.application.board.dto.UpdatePostingRequestDto;
import team.lindo.backend.application.board.entity.Posting;
import team.lindo.backend.application.board.repository.PostingRepository;
import team.lindo.backend.application.product.entity.Product;
import team.lindo.backend.application.product.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostingService {
    private final PostingRepository postingRepository;
    private final ProductRepository productRepository;

    // CR
    public Posting createPosting(Posting posting) {
        return postingRepository.save(posting);
    }

    // U
    @Transactional
    public Posting updatePosting(Long postingId, UpdatePostingRequestDto request) {
        // 게시물 조회
        Posting posting = postingRepository.findById(postingId)
                .orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));

        // 제목 수정
        if (request.getTitle() != null && !request.getTitle().isBlank()) {
            posting.updateTitle(request.getTitle());
        }

        // 본문 수정
        if (request.getContent() != null && !request.getContent().isBlank()) {
            posting.updateContent(request.getContent());
        }

        // 이미지 URL 수정
        if (request.getImageUrl() != null && !request.getImageUrl().isBlank()) {
            posting.updateImageUrl(request.getImageUrl());
        }

        // 연결된 제품 수정
        if (request.getProductIds() != null && !request.getProductIds().isEmpty()) {
            List<Product> products = productRepository.findAllById(request.getProductIds());
            posting.updatePostingProducts(products);
        }

        return posting;
    }


    // D
    public void deletePosting(Long postingId) {
        postingRepository.deleteById(postingId);
    }

    // 전체 게시물 조회
    public List<Posting> getAllPostings() {
        return postingRepository.findAll();
    }

    // 댓글 많은 순서 게시물 조회
    public List<Posting> getPostingsByComments() {
        return postingRepository.findAllByComments();
    }

    // 최신순 게시물 조회
    public List<Posting> getPostingsByLatest() {
        return postingRepository.findAllByOrderByCreatedDesc();
    }

    // 좋아요 순서 게시물 조회
    public List<Posting> getPostingsByLikes() {
        return postingRepository.findAllByLikes();
    }

    // 제목 혹은 내용으로 게시물 검색
    public List<Posting> searchPostingsByKeyword(String keyword) {
        return postingRepository.searchByTitleOrContent(keyword);
    }

    // 특정 카테고리 제품을 포함하는 게시물 조회
    public List<Posting> getPostingsByCategory(Long categoryId) {  // Pageable 추가해서 페이징 지원?
        return postingRepository.findByCategoryId(categoryId);
    }

    // 특정 제품이 포함된 게시물 조회
    public List<Posting> getPostingsByProduct(Long productId) {
        return postingRepository.findByProductId(productId);
    }
}
