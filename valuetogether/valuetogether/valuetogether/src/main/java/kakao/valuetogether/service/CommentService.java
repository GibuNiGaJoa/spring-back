package kakao.valuetogether.service;

import kakao.valuetogether.domain.Comment;
import kakao.valuetogether.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Long enroll(Comment comment) {
        commentRepository.save(comment);
        return comment.getId();
    }

    public void addLikes(Long id) {
        Comment findComment = commentRepository.findById(id);
        commentRepository.addLikes(findComment);
    }

    public void removeLikes(Long id) {
        Comment findComment = commentRepository.findById(id);
        commentRepository.removeLikes(findComment);
    }
}
