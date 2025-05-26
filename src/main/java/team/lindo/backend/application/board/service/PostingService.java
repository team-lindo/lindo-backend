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
import team.lindo.backend.application.user.repository.UserRepository;
import team.lindo.backend.common.util.SecurityUtil;
import team.lindo.backend.util.S3Uploader;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostingService {
    private final PostingRepository postingRepository;
    private final ProductRepository productRepository;
    private final CommentRepository commentRepository;
    private final PostImageRepository postImageRepository;
    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public PostingSummaryDto createPosting(CreatePostingRequestDto request) {
        Long userId = SecurityUtil.getCurrentUserId(); // 현재 로그인된 사용자 ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        Posting posting = Posting.builder()
                .user(user)
                .content(request.getContent())
                .imageUrls(request.getImageUrls())
                .hashtags(request.getHashtags() != null ? request.getHashtags() : Set.of())
                .build();

        if (request.getTaggedProducts() != null) {
            for (TaggedProductGroupDto group : request.getTaggedProducts()) {
                Long imageId = Long.parseLong(group.getImageId());

                for (TaggedProductDto tag : group.getTags()) {
                    Product product = productRepository.findById(tag.getUid())
                            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다: " + tag.getUid()));

                    PostingProduct postingProduct = PostingProduct.builder()
                            .posting(posting)
                            .product(product)
                            .x(tag.getX())
                            .y(tag.getY())
                            .imageId(imageId) // 중요!
                            .build();

                    posting.getPostingProducts().add(postingProduct);
                }
            }
        }

        if (request.getImageUrls() != null) {
            for (String imageUrl : request.getImageUrls()) {
                PostImage image = postImageRepository.findByImageUrl(imageUrl)
                        .orElseThrow(() -> new IllegalArgumentException("해당 이미지 URL에 대한 이미지를 찾을 수 없습니다: " + imageUrl));
                posting.addPostImage(image); // 연관관계 설정
            }
        }
        Posting saved = postingRepository.save(posting);

        return new PostingSummaryDto(saved);  //! Posting의 다른 연관관계 필드들은? 이렇게만 생성하면 게시물에 제품(정보)들 없는 꼴 아닌가?
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

        List<String> imageUrls = new ArrayList<>(post.getImageUrls());
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

        List<TaggedProductGroupDto> grouped = taggedMap.entrySet().stream()
                .map(e -> new TaggedProductGroupDto(e.getKey().toString(), e.getValue()))
                .toList();

        return LoadPostResponseDto.builder()
                .id(post.getId())
                .user(new UserSummaryDto(post.getUser()))
                .content(post.getContent())
                .images(imageUrls)
                .comments(commentDtos)
                .taggedProducts(grouped)
                .build();
    }

    public List<UploadImageResponseDto> uploadImages(MultipartFile[] images) {
        return Arrays.stream(images)
                .map(image -> {
                    String url = s3Uploader.upload(image, "post-images"); // 서버/S3에 저장
                    PostImage postImage = PostImage.builder()
                            .imageUrl(url)
                            .build(); // 현재는 posting 없이 저장
                    postImageRepository.save(postImage);

                    return new UploadImageResponseDto(
                            postImage.getId().toString(),
                            postImage.getImageUrl()
                    );
                })
                .collect(Collectors.toList());
    }
}
