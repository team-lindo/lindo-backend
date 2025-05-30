package team.lindo.backend.application.social.service;

//import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.lindo.backend.application.social.dto.*;
import team.lindo.backend.application.social.entity.Follow;
import team.lindo.backend.application.social.repository.follow.FollowRepository;
import team.lindo.backend.application.user.entity.User;
import team.lindo.backend.application.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    // 팔로우 추가
    @Transactional
    public void followUser(Long followerId, Long followingId) {
        if(followerId.equals(followingId)) {
            throw new IllegalArgumentException("자기 자신을 팔로우할 수 없습니다.");
        }

        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("팔로우 요청 사용자가 존재하지 않습니다."));
        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new IllegalArgumentException("팔로우 대상 사용자가 존재하지 않습니다."));

        followRepository.findByFollowerIdAndFollowingId(followerId, followingId)
                .ifPresent(f -> {
                    throw new IllegalArgumentException("이미 팔로우한 사용자입니다.");
                });

        Follow follow = Follow.builder()
                .follower(follower)
                .following(following)
                .build();
        followRepository.save(follow);
    }

    @Transactional(readOnly = true)
    public FollowResponseDto followInfo(Long myId, Long followingId) {
        // 1. 팔로우한 유저 정보 (내가 팔로우한 대상)
        User followedUser = userRepository.findById(followingId)
                .orElseThrow(() -> new IllegalArgumentException("팔로우한 사용자가 존재하지 않습니다."));

        // 2. 나의 팔로잉/팔로워 수
        Long followingCount = followRepository.countByFollowerId(myId);
        Long followerCount = followRepository.countByFollowingId(myId);

        // 3. 응답 DTO 생성
        FollowingDto followedDto = new FollowingDto(followedUser.getId(), followedUser.getNickname());

        return new FollowResponseDto(myId, followedDto, followingCount, followerCount);
    }

    // 팔로우 취소
    @Transactional
    public UnfollowResponseDto unfollowUser(Long followerId, Long followingId) {
        Follow follow = followRepository.findByFollowerIdAndFollowingId(followerId, followingId)
                        .orElseThrow(() -> new IllegalArgumentException("팔로우 관계가 존재하지 않습니다."));

        followRepository.delete(follow);

        Long followingCount = getFollowingCount(followerId);
        Long followerCount = getFollowerCount(followerId);

        return new UnfollowResponseDto(followingId, followingCount, followerCount);
    }


    // 특정 사용자의 팔로우 목록 조회
    // GPT는 org.springframework.transaction.annotation.Transactional 사용해서 readOnly=true 걸어줬던데
    // Transactional jakarta꺼 import해서 쓰면 안되나? 저걸로 써야 하나?
    @Transactional(readOnly = true)
    public List<Follow> getFollowingList(Long followerId) {
        return followRepository.findByFollowerId(followerId);
    }

    // 특정 사용자의 팔로워 목록 조회
    @Transactional(readOnly = true)
    public List<Follow> getFollowerList(Long followingId) {
        return followRepository.findByFollowingId(followingId);
    }

    // 팔로우 여부 확인
    @Transactional(readOnly = true)
    public boolean isFollowing(Long followerId, Long followingId) {
        return followRepository.findByFollowerIdAndFollowingId(followerId, followingId).isPresent();
    }

    // 특정 사용자의 팔로우 수 조회
    @Transactional(readOnly = true)
    public Long getFollowingCount(Long followerId) {
        return followRepository.countByFollowerId(followerId);
    }

    // 특정 사용자의 팔로워 수 조회
    @Transactional(readOnly = true)
    public Long getFollowerCount(Long followingId) {
        return followRepository.countByFollowingId(followingId);
    }

    // 프론트 맞춤 팔로워 리스트와 팔로워 수
    @Transactional(readOnly = true)
    public LoadFollowersResponseDto loadFollowers(Long userId) {
        List<Follow> followers = followRepository.findByFollowingId(userId);

        List<FollowerDto> followerList = followers.stream()
                .map(f -> new FollowerDto(f.getFollower().getId(), f.getFollower().getNickname()))
                .toList();

        Long followerCount = getFollowerCount(userId); //

        return LoadFollowersResponseDto.builder()
                .users(followerList)
                .totalCount(followerCount)
                .build();
    }
    // 프론트 맞춤 팔로잉 리스트와 팔로잉 수
    @Transactional(readOnly = true)
    public LoadFollowingsResponseDto loadFollowings(Long userId) {
        List<Follow> followings = followRepository.findByFollowerId(userId);

        List<FollowingDto> followingList = followings.stream()
                .map(f -> new FollowingDto(f.getFollowing().getId(), f.getFollowing().getNickname()))
                .toList();

        Long followingCount = getFollowingCount(userId); //

        return LoadFollowingsResponseDto.builder()
                .users(followingList)
                .totalCount(followingCount)
                .build();
    }
}
