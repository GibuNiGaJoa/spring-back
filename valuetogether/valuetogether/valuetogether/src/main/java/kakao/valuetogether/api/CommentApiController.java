package kakao.valuetogether.api;

import kakao.valuetogether.domain.Comment;
import kakao.valuetogether.domain.Member;
import kakao.valuetogether.domain.Post;
import kakao.valuetogether.service.CommentService;
import kakao.valuetogether.service.JwtService;
import kakao.valuetogether.service.MemberService;
import kakao.valuetogether.service.PostService;
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

    @PostMapping("fundraisings/{id}/comment")
    public CreatedCommentResponse enrollComment(@RequestHeader(value = "Authorization") String token, @PathVariable("id") Long id, @RequestBody @Valid CreatedCommentRequest request) {
        Long memberId = jwtService.parseJwtToken(token);
        Member findMember = memberService.findOne(memberId);
        Post findPost = postService.findOneById(id);
        Comment comment = new Comment(findMember, findPost);
        comment.setContent(request.getContent());
        comment.setDate(request.getDate());

        commentService.enroll(comment);
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
        Long memberId = jwtService.parseJwtToken(token);
        commentService.addLikes(id);
        return new LikeResponse(true);
    }
    @GetMapping("fundraisings/{id}/removelike")
    public LikeResponse removeLike(@RequestHeader(value = "Authorization") String token,@PathVariable("id") Long id) {
        Long memberId = jwtService.parseJwtToken(token);
        commentService.removeLikes(id);
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
