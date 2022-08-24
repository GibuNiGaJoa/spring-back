package kakao.valuetogether.service;

import kakao.valuetogether.domain.Comment;
import kakao.valuetogether.domain.Member;
import kakao.valuetogether.domain.Post;
import kakao.valuetogether.dto.CommentVO;
import kakao.valuetogether.dto.MyPageCommentDTO;
import kakao.valuetogether.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

    public MyPageCommentDTO getMyPageCommentDTO(Member member) {
        AtomicInteger countComment = new AtomicInteger();

        List<CommentVO> commentVOs = new ArrayList<>();

        List<Comment> comments = commentRepository.findCommentsByMember(member);
        comments.forEach(comment -> {
            countComment.incrementAndGet();

            CommentVO commentVO = CommentVO.builder()
                    .postTitle(comment.getPost().getTitle())
                    .content(comment.getContent())
                    .date(comment.getDate())
                    .likes(comment.getLikes())
                    .build();
            commentVOs.add(commentVO);
        });

        MyPageCommentDTO result = MyPageCommentDTO.builder()
                .countComment(countComment.intValue())
                .commentVOs(commentVOs).build();
        return result;
    }
}
