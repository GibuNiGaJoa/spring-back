package kakao.valuetogether.api;

import kakao.valuetogether.domain.Member;
import kakao.valuetogether.domain.Post;
import kakao.valuetogether.domain.Tag;
import kakao.valuetogether.repository.TagRepository;
import kakao.valuetogether.service.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController // @Controller + @ResponseBody가 이 어노테이션에 포함된다.
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;
    private final MemberService memberService;

    private final TagService tagService;

    private final TagPostService tagPostService;


    //기부 제안하기
    @PostMapping("/fundraisings/propose/project")
    public ProposeResponse propose(@RequestBody @Valid ProposeRequest request){
        //Long MemberId = jwtService.parseJwtToken(token);

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setSubTitle(request.getSubTitle());
//        post.setTargetAmount(request.getTargetAmount());
        post.setContent(request.getContent());
//        post.setStartDate(request.getStartDate());
//        post.setEndDate(request.getEndDate());
        Member findMember = memberService.findOne(1L);
        post.setMember(findMember);

        Long postId = postService.propose(post);

        return new ProposeResponse(true);
    }
    @Data
    static class ProposeRequest {
        private String title;

        private String subTitle;

        //private Integer targetAmount;

        private String content;

//        private Date startDate;
//
//        private Date endDate;

    }
    @Data
    static class ProposeResponse {
        private Boolean status;

        public ProposeResponse(Boolean status) {
            this.status = status;
        }
    }
}
