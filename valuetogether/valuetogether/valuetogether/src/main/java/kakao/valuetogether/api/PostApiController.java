package kakao.valuetogether.api;

import kakao.valuetogether.domain.Member;
import kakao.valuetogether.domain.Post;
import kakao.valuetogether.domain.Tag;
import kakao.valuetogether.service.JwtService;
import kakao.valuetogether.service.MemberService;
import kakao.valuetogether.service.PostService;
import kakao.valuetogether.service.TagService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController // @Controller + @ResponseBody가 이 어노테이션에 포함된다.
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    private final MemberService memberService;

    private final TagService tagService;

    private final JwtService jwtService;

    @PostMapping("/propose/project")
    public CreatedPostResponse proposePost(@RequestHeader(value = "Authorization") String token,@RequestBody @Valid CreatedPostRequest request) {
        Long memberId = jwtService.parseJwtToken("Bearer " + token);
        Member findMember = memberService.findOne(memberId);
        Post post = new Post();
        post.setMember(findMember);
        post.setTitle(request.getA());
        post.setSubTitle(request.getB());
        post.setContent(request.getC());

        postService.propose(post);
        return new CreatedPostResponse(true);
    }

    @Data
    static class CreatedPostRequest {
        private String a;
        private String b;
        private String c;
    }

    @Data
    static class CreatedPostResponse {
        private Boolean status;

        public CreatedPostResponse(Boolean status) {
            this.status = status;
        }
    }

    @PostMapping("/savetag")
    public CreatedTagResponse saveMember(@RequestBody @Valid CreatedTagRequest request) {

        Tag tag = new Tag(request.getTagName());
        tagService.addTag(tag);

        return new CreatedTagResponse(true);
    }

    @Data
    static class CreatedTagRequest {
        private String tagName;
    }

    @Data
    static class CreatedTagResponse {
        private Boolean status;

        public CreatedTagResponse(Boolean status) {
            this.status = status;
        }
    }
}
