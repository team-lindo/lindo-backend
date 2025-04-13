package team.lindo.backend.application.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.lindo.backend.application.board.entity.Posting;
import team.lindo.backend.application.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    public Optional<User> findByUsername(String username);
    public boolean existsByUsername(String username);
}
