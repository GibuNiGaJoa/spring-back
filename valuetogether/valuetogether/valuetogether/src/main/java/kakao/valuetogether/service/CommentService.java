package kakao.valuetogether.service;

import kakao.valuetogether.domain.Comment;
import kakao.valuetogether.domain.LikeDetail;
import kakao.valuetogether.domain.Member;
import kakao.valuetogether.domain.Post;
import kakao.valuetogether.dto.CommentResponseDTO;
import kakao.valuetogether.repository.CommentRepository;
import kakao.valuetogether.repository.LikeDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final LikeDetailRepository likeDetailRepository;

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

    public List<CommentResponseDTO> findComments(Post post, Member member) {
        List<Comment> findComments = commentRepository.findCommentByPost(post);
        List<CommentResponseDTO> commentList = null;
        for (int i = 0; i < findComments.size(); i++) {
            Optional<LikeDetail> findLikeDetail = likeDetailRepository.findStatus(findComments.get(i), member);
            if (findLikeDetail.isPresent()) {
                commentList.add(new CommentResponseDTO(findComments.get(i).getId(), findComments.get(i).getMember().getNickname(),
                        findComments.get(i).getContent(), findComments.get(i).getDate(),findComments.get(i).getLikes(),true));
            } else {
                commentList.add(new CommentResponseDTO(findComments.get(i).getId(), findComments.get(i).getMember().getNickname(),
                        findComments.get(i).getContent(), findComments.get(i).getDate(),findComments.get(i).getLikes(), false));
            }
        }
        return commentList;
    }

    public Comment findOne(Long id) {
        return commentRepository.findById(id);
    }
}
