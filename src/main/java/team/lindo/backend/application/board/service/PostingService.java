package team.lindo.backend.application.board.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team.lindo.backend.application.board.dto.PostRequestDto;
import team.lindo.backend.application.board.dto.PostingSummaryDto;
import team.lindo.backend.application.board.dto.UpdatePostingRequestDto;
import team.lindo.backend.application.board.entity.Posting;
import team.lindo.backend.application.board.repository.posting.PostingRepository;
import team.lindo.backend.application.product.entity.Product;
import team.lindo.backend.application.product.repository.ProductRepository;
import team.lindo.backend.application.user.entity.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostingService {
    private final PostingRepository postingRepository;
    private final ProductRepository productRepository;

    // CR
    public Posting createPosting(PostRequestDto request, User user) {
        return postingRepository.save(
                Posting.builder()
                        .user(user)
                        .title(request.getTitle())
                        .content(request.getContent())
                        .imageUrl(request.getImageUrl())
                        .build()
        );  //! Posting의 다른 연관관계 필드들은? 이렇게만 생성하면 게시물에 제품(정보)들 없는 꼴 아닌가?
    }

    // U
    @Transactional
    public PostingSummaryDto updatePosting(Long postingId, UpdatePostingRequestDto request) {
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

        return new PostingSummaryDto(posting);
    }

    // D
    public void deletePosting(Long postingId) {
        postingRepository.deleteById(postingId);
    }

    // 게시물 목록 조회
    public Page<PostingSummaryDto> getAllPostings(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Posting> postings = postingRepository.findAll(pageRequest);

        return postings.map(PostingSummaryDto::new);
    }

    // 특정 게시물 조회
    public PostingSummaryDto getPostingById(Long postingId) {
        Posting posting = postingRepository.findById(postingId)
                .orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));
        return new PostingSummaryDto(posting);
    }

    //! 리턴 타입 싹 다 entity가 아닌 DTO로 수정???
    // 댓글 많은 순서 게시물 조회
    public List<Posting> getPostingsByComments() {
        return postingRepository.findAllByComments();
    }

    // 최신순 게시물 조회
    public List<Posting> getPostingsByLatest() {
        return postingRepository.findAllByOrderByCreatedAtDesc();
    }

    // 좋아요 순서 게시물 조회
    public List<Posting> getPostingsByLikes() {
        return postingRepository.findAllByLikes();
    }

    // 제목 혹은 내용으로 게시물 검색
    public List<PostingSummaryDto> searchPostingsByKeyword(String keyword) {
        List<Posting> postings = postingRepository.searchByTitleOrContent(keyword);
        return postings.stream().map(PostingSummaryDto::new).toList();
    }

    // 특정 카테고리 제품을 포함하는 게시물 조회
    public List<Posting> getPostingsByCategory(Long categoryId) {  // Pageable 추가해서 페이징 지원?
        return postingRepository.findByCategoryId(categoryId);
    }

    // 특정 제품이 포함된 게시물 조회
    public List<Posting> getPostingsByProduct(Long productId) {
        return postingRepository.findByProductId(productId);
    }

    // 특정 사용자가 작성한 게시물들 조회
    public List<Posting> getPostingsByUser(Long userId) {
        return postingRepository.findPostingsByUserId(userId);
    }

    // 특정 사용자가 좋아요를 누른 게시물들 조회
    public List<Posting> getLikedPostingsByUser(Long userId) {
        return postingRepository.findLikedPostingsByUserId(userId);
    }

    // 특정 사용자가 댓글을 작성한 게시물들 조회
    public List<Posting> getCommentedPostingsByUser(Long userId) {
        return postingRepository.findCommentedPostingsByUserId(userId);
    }

    // 특정 사용자가 팔로우한 사람이 작성한 게시물들 조회
    public List<Posting> getFollowingPostingsByUser(Long userId) {
        return postingRepository.findFollowingPostingsByUserId(userId);
    }
}
