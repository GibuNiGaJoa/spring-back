package kakao.valuetogether.api;

import kakao.valuetogether.domain.Member;
import kakao.valuetogether.domain.Post;
import kakao.valuetogether.service.MemberService;
import kakao.valuetogether.service.PostService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController // @Controller + @ResponseBody가 이 어노테이션에 포함된다.
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    private final MemberService memberService;

    @PutMapping("/propose/project")
    public CreatedPostResponse proposePost(@RequestBody @Valid CreatedPostRequest request) {
        Member findMember = memberService.findOne(1L);
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
}
