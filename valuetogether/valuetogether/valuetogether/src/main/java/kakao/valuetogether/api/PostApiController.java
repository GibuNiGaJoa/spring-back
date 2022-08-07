package kakao.valuetogether.api;

import kakao.valuetogether.domain.Member;
import kakao.valuetogether.domain.Post;

import kakao.valuetogether.service.*;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController // @Controller + @ResponseBody가 이 어노테이션에 포함된다.
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;
    private final MemberService memberService;

    //기부 제안하기
    @PostMapping("/propose/project")
    public ProposeResponse propose(@RequestBody @Valid ProposeRequest request){

        Post post = new Post();
        post.setTitle(request.getA());
        post.setSubTitle(request.getB());
        post.setContent(request.getC());

        Member findMember = memberService.findOne(1L);
        post.setMember(findMember);

        postService.propose(post);

        return new ProposeResponse(true);
    }
    @Data
    static class ProposeRequest {
        private String a;

        private String b;

        private String c;
    }
    @Data
    static class ProposeResponse {
        private Boolean status;

        public ProposeResponse(Boolean status) {
            this.status = status;
        }
    }
}
