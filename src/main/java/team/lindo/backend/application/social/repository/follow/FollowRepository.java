package team.lindo.backend.application.social.repository.follow;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.lindo.backend.application.social.entity.Follow;
import team.lindo.backend.application.user.entity.User;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long>, FollowCustomRepository {
}
