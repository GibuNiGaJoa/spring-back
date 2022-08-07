package kakao.valuetogether.api;

import kakao.valuetogether.domain.Post;
import kakao.valuetogether.domain.Tag;
import kakao.valuetogether.repository.TagRepository;
import kakao.valuetogether.service.JwtService;
import kakao.valuetogether.service.PostService;
import kakao.valuetogether.service.TagPostService;
import kakao.valuetogether.service.TagService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController // @Controller + @ResponseBody가 이 어노테이션에 포함된다.
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    private final TagService tagService;

    private final TagPostService tagPostService;

    private final JwtService jwtService;

    //기부 제안하기
    @PostMapping("/fundraisings/propose/project")
    public ProposeResponse propose(@RequestBody @Valid ProposeRequest request){
        //Long MemberId = jwtService.parseJwtToken(token);

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setSubTitle(request.getSubTitle());
        post.setTargetAmount(request.getTargetAmount());
        post.setContent(request.getContent());
        post.setStartDate(request.getStartDate());
        post.setEndDate(request.getEndDate());
        post.setId(1L);

        Long postId = postService.propose(post);

        return new ProposeResponse(true);
    }
    @Data
    static class ProposeRequest {
        private String tag;

        private String topic;

        private String target;

        private String title;

        private String subTitle;

        private Integer targetAmount;

        private String content;

        private Date startDate;

        private Date endDate;

        private String link;
    }
    @Data
    static class ProposeResponse {
        private Boolean status;

        public ProposeResponse(Boolean status) {
            this.status = status;
        }
    }
}
