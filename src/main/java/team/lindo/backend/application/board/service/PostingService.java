package team.lindo.backend.application.board.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team.lindo.backend.application.board.dto.*;
import team.lindo.backend.application.board.entity.Comment;
import team.lindo.backend.application.board.entity.PostImage;
import team.lindo.backend.application.board.entity.Posting;
import team.lindo.backend.application.board.entity.PostingProduct;
import team.lindo.backend.application.board.repository.PostImage.PostImageRepository;
import team.lindo.backend.application.board.repository.posting.PostingRepository;
import team.lindo.backend.application.board.repository.comment.CommentRepository;
import team.lindo.backend.application.product.entity.Product;
import team.lindo.backend.application.product.repository.ProductRepository;
import team.lindo.backend.application.user.dto.UserSummaryDto;
import team.lindo.backend.application.user.entity.User;
import team.lindo.backend.util.StorageUtil;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostingService {
    private final PostingRepository postingRepository;
    private final ProductRepository productRepository;
    private final CommentRepository commentRepository;
    private final PostImageRepository postImageRepository;
    private final StorageUtil storageUtil;
    // 이미지 업로드
    public List<UploadImageResponseDto> savePostImages(List<String> imageUrls, Long postId) {
        Posting posting = postingRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

        return imageUrls.stream()
                .map(url -> {
                    PostImage postImage = PostImage.builder()
                            .imageUrl(url)
                            .posting(posting)
                            .build();
                    postImageRepository.save(postImage);
                    return new UploadImageResponseDto(postImage.getId().toString(), postImage.getImageUrl());
                })
                .collect(Collectors.toList());
    }
    public Posting createPosting(CreatePostingRequestDto request, User user) {
        Posting posting = Posting.builder()
                .user(user)
                .content(request.getContent())
                .imageUrls(request.getImageUrls())
                .hashtags(request.getHashtags() != null ? request.getHashtags() : Set.of())
                .build();

        if (request.getTaggedProducts() != null) {
            for (TaggedProductDto tag : request.getTaggedProducts()) {
                Product product = productRepository.findById(tag.getUid())
                        .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다: " + tag.getUid()));

                PostingProduct postingProduct = PostingProduct.builder()
                        .posting(posting)
                        .product(product)
                        .x(tag.getX())
                        .y(tag.getY())
                        .build();

                posting.getPostingProducts().add(postingProduct);
            }
        }

        if (request.getImageUrls() != null) {
            for (String imageUrl : request.getImageUrls()) {
                PostImage image = postImageRepository.findByImageUrl(imageUrl)
                        .orElseThrow(() -> new IllegalArgumentException("해당 이미지 URL에 대한 이미지를 찾을 수 없습니다: " + imageUrl));
                image.setPosting(posting); // 연관관계 설정
            }
        }
        return postingRepository.save(posting);  //! Posting의 다른 연관관계 필드들은? 이렇게만 생성하면 게시물에 제품(정보)들 없는 꼴 아닌가?
    }

    // U
    @Transactional
    public Posting update(Long postId, String newContent) {
        Posting posting = postingRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        posting.updateContent(newContent);
        return posting;
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
//    public List<PostingSummaryDto> searchPostingsByKeyword(String keyword) {
//        List<Posting> postings = postingRepository.searchByTitleOrContent(keyword);
//        return postings.stream().map(PostingSummaryDto::new).toList();
//    }

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
    // 프론트엔드 맞춤 다음 페이지가 있는지 확인하고 무한 스크롤
    public PostPageResponseDto getPostPreviews(Pageable pageable) {
        Page<Posting> page = postingRepository.findAll(pageable);

        List<PostDto> posts = page.getContent().stream()
                .map(PostDto::new)
                .toList();
        return PostPageResponseDto.builder()
                .posts(posts)
                .hasNext(page.hasNext())  // 다음 페이지가 있는지
                .build();
    }

    // 프론트 요청  특정 포스트를 조회할때 정보 가져오기
    @Transactional
    public LoadPostResponseDto loadPost(Long postId) {
        Posting post = postingRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));

        // 댓글 조회
        List<Comment> comments = commentRepository.findByPostingId(postId);
        List<CommentDto> commentDtos = comments.stream()
                .map(CommentDto::new)
                .toList();

        // 태깅된 상품들: (imageKey -> TaggedProductDto 리스트)
        Map<Long, List<TaggedProductDto>> taggedMap = new HashMap<>();

        for (PostingProduct pp : post.getPostingProducts()) {
            // 여기서 imageId 또는 이미지 index를 key로 사용한다면 조정 필요 (예시는 고정값 0L로 단순화)
            Long imageKey = 0L; // 실제 로직에 맞게 이미지별 ID 또는 순번으로 조정
            TaggedProductDto tagged = new TaggedProductDto(
                    pp.getProduct().getId(),
                    pp.getProduct().getName(),
                    pp.getProduct().getPrice(),
                    pp.getX(),
                    pp.getY()
            );
            taggedMap.computeIfAbsent(imageKey, k -> new ArrayList<>()).add(tagged);
        }

        return LoadPostResponseDto.builder()
                .id(post.getId())
                .user(new UserSummaryDto(post.getUser()))
                .content(post.getContent())
                .images(post.getImageUrls())
                .comments(commentDtos)
                .taggedProducts(taggedMap)
                .build();
    }

    public List<UploadImageResponseDto> uploadImages(MultipartFile[] images) {
        List<UploadImageResponseDto> result = new ArrayList<>();
        for (MultipartFile image : images) {
            // 1. 로컬/클라우드 스토리지에 저장
            String storedUrl = storageUtil.save(image); // 예: S3 또는 로컬 디렉토리
            String uuid = UUID.randomUUID().toString();
            result.add(new UploadImageResponseDto(uuid, storedUrl));
        }
        return result;
    }
}
