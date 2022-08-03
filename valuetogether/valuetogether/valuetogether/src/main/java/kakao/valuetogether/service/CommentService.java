package kakao.valuetogether.service;

import kakao.valuetogether.domain.Comment;
import kakao.valuetogether.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Long enrollComment(Comment comment) {
        commentRepository.saveComment(comment);
        return comment.getId();
    }

    public Long editComment(Comment comment, String content) {
        return commentRepository.updateComment(comment, content);
    }

    public void removeComment(Comment comment) {
        commentRepository.deleteComment(comment);
    }

    public void clickLike(Comment comment) {
        commentRepository.addLikes(comment);
    }

    public void disLike(Comment comment) {
        commentRepository.minusLikes(comment);
    }
}
