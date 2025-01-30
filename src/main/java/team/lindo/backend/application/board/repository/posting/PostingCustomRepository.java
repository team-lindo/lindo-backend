package team.lindo.backend.application.board.repository.posting;

import team.lindo.backend.application.board.entity.Posting;

import java.util.List;

public interface PostingCustomRepository {
    List<Posting> findPostingByCategoryId(Long categoryId);
}
