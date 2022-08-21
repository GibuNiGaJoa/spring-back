package kakao.valuetogether.api;

import kakao.valuetogether.domain.Comment;
import kakao.valuetogether.domain.LikeDetail;
import kakao.valuetogether.domain.Member;
import kakao.valuetogether.domain.Post;
import kakao.valuetogether.domain.enums.DonationType;
import kakao.valuetogether.dto.DonationRequestDTO;
import kakao.valuetogether.service.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;
    private final MemberService memberService;
    private final JwtService jwtService;

    private final PostService postService;
    private final LikeDetailService likeDetailService;
    private final DonationService donationService;

    @PostMapping("fundraisings/{id}/comment")
    public CreatedCommentResponse enrollComment(@RequestHeader(value = "Authorization") String token,
                                                @PathVariable("id") Long postId,
                                                @RequestBody @Valid CreatedCommentRequest request) {

        Long memberId = jwtService.parseJwtToken("Bearer " + token);
        Member findMember = memberService.findOne(memberId);

        Post findPost = postService.findOneById(postId);

        Comment comment = new Comment(findMember, findPost);
        comment.setContent(request.getContent());
        comment.setDate(request.getDate());

        commentService.enroll(comment);

        donationService.donateComment(findMember, memberId, request.getDate());

        return new CreatedCommentResponse(true);
    }

    @Data
    static class CreatedCommentRequest {
        private String content;
        private Date date;
    }

    @Data
    static class CreatedCommentResponse {
        private Boolean status;

        public CreatedCommentResponse(Boolean status) {
            this.status = status;
        }
    }
    @GetMapping("fundraisings/{id}/addlike")
    public LikeResponse addLike(@RequestHeader(value = "Authorization") String token,@PathVariable("id") Long id) {
        Long memberId = jwtService.parseJwtToken("Bearer " + token);
        commentService.addLikes(id);
        Member findMember = memberService.findOne(memberId);
        Comment comment = commentService.findOne(id);

        LikeDetail likeDetail = new LikeDetail(comment, findMember);
        likeDetailService.save(likeDetail);

        return new LikeResponse(true);
    }
    @GetMapping("fundraisings/{id}/removelike")
    public LikeResponse removeLike(@RequestHeader(value = "Authorization") String token,@PathVariable("id") Long id) {
        Long memberId = jwtService.parseJwtToken("Bearer " + token);
        commentService.removeLikes(id);
        Member findMember = memberService.findOne(memberId);
        Comment comment = commentService.findOne(id);

        LikeDetail findLikeDetail = likeDetailService.findOne(comment, findMember);
        likeDetailService.delete(findLikeDetail);

        return new LikeResponse(true);
    }
    @Data
    static class LikeResponse {
        private Boolean status;

        public LikeResponse(Boolean status) {
            this.status = status;
        }
    }
}
