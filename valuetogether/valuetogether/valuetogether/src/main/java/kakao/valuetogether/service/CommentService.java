package kakao.valuetogether.service;

import kakao.valuetogether.domain.Comment;
import kakao.valuetogether.domain.Member;
import kakao.valuetogether.domain.Post;
import kakao.valuetogether.dto.CommentVO;
import kakao.valuetogether.dto.MyPageCommentDTO;
import kakao.valuetogether.domain.LikeDetail;
import kakao.valuetogether.dto.CommentResponseDTO;
import kakao.valuetogether.repository.CommentRepository;
import kakao.valuetogether.repository.LikeDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
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
        List<CommentResponseDTO> commentList = new ArrayList<>();
        Optional<LikeDetail> findLikeDetail;
        for (Comment c : findComments) {
            findLikeDetail = likeDetailRepository.findStatus(c, member);
            if (c.getMember() == member) {
                if (findLikeDetail.isEmpty()) {
                    commentList.add(new CommentResponseDTO(c.getId(), c.getMember().getNickname(),
                            c.getContent(), c.getDate(), c.getLikes(), false, true,c.getDonationAmount(),c.getMember().getGender()));
                } else {
                    commentList.add(new CommentResponseDTO(c.getId(), c.getMember().getNickname(),
                            c.getContent(), c.getDate(), c.getLikes(), true, true,c.getDonationAmount(),c.getMember().getGender()));
                }

            } else {
                if (findLikeDetail.isEmpty()) {
                    commentList.add(new CommentResponseDTO(c.getId(), c.getMember().getNickname(),
                            c.getContent(), c.getDate(), c.getLikes(), false, false,c.getDonationAmount(),c.getMember().getGender()));
                } else {
                    commentList.add(new CommentResponseDTO(c.getId(), c.getMember().getNickname(),
                            c.getContent(), c.getDate(), c.getLikes(), true, false,c.getDonationAmount(),c.getMember().getGender()));
                }
            }
        }
        return commentList;
    }

    public Comment findOne(Long id) {
        return commentRepository.findById(id);
    }

    public MyPageCommentDTO getMyPageCommentDTO(Member member) {
//        AtomicInteger countComment = new AtomicInteger();
//
//        List<CommentVO> commentVOs = new ArrayList<>();
//
//        List<Comment> comments = commentRepository.findCommentsByMember(member);
//        comments.forEach(comment -> {
//            countComment.incrementAndGet();
//
//            CommentVO commentVO = CommentVO.builder()
//                    .postTitle(comment.getPost().getTitle())
//                    .content(comment.getContent())
//                    .date(comment.getDate())
//                    .likes(comment.getLikes())
//                    .build();
//            commentVOs.add(commentVO);
//        });
        List<CommentVO> commentList = new ArrayList<>();
        Optional<LikeDetail> findLikeDetail;
        List<Comment> findComments = commentRepository.findCommentsByMember(member);
        for (Comment c : findComments){
            findLikeDetail = likeDetailRepository.findStatus(c, member);
            if (findLikeDetail.isEmpty()) {
                commentList.add(new CommentVO(c.getId(), c.getPost(), c.getContent(), c.getDate(), c.getLikes(), c.getPost().getTitle(), false));
            } else {
                commentList.add(new CommentVO(c.getId(), c.getPost(), c.getContent(), c.getDate(), c.getLikes(), c.getPost().getTitle(), true));
            }
        }
        Integer countComment = findComments.size();

        MyPageCommentDTO result = MyPageCommentDTO.builder()
                .countComment(countComment)
                .commentVOs(commentList).build();
        return result;
    }

    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }
}
