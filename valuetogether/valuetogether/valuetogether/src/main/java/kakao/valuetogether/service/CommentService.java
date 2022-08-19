package kakao.valuetogether.service;

import kakao.valuetogether.domain.Comment;
import kakao.valuetogether.domain.Post;
import kakao.valuetogether.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Comment> findComment(Post post) {
        return commentRepository.findCommentByPost(post);
    }

    public Comment findOne(Long id) {
        return commentRepository.findById(id);
    }
}
