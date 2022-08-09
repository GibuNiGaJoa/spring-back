package kakao.valuetogether.api;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.util.JSONPObject;
import kakao.valuetogether.domain.*;
import kakao.valuetogether.service.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@RestController // @Controller + @ResponseBody가 이 어노테이션에 포함된다.
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    private final MemberService memberService;

    private final TagService tagService;

    private final TagPostService tagPostService;

    private final LinkService linkService;
    private final JwtService jwtService;

    @GetMapping ("fundraisings/propose")
    public ProposeResponse propose(@RequestHeader(value = "Authorization") String token) {
        Long findId = jwtService.parseJwtToken("Bearer " + token);//토큰 검증
        Member findMember = memberService.findOne(findId);
        return new ProposeResponse(findMember.getName());
    }

    @Data
    static class ProposeResponse {
        private String proposer;

        public ProposeResponse(String proposer) {
            this.proposer = proposer;
        }
    }
    //기부 제안하기
    @PostMapping("fundraisings/propose/project")
    public CreatedPostResponse proposePost(@RequestBody @Valid CreatedPostRequest request) {

        //Long memberId = jwtService.parseJwtToken("Bearer " + token);
        Member findMember = memberService.findOne(1L);
        Post post = new Post();
        post.setMember(findMember);
        post.setTitle(request.getTitle());
        post.setProposer(request.getProposer());
        post.setContent(request.getContent());
        post.setTargetAmount(request.getTargetAmount());
        post.setStartDate(request.getStartDate());
        post.setEndDate(request.getEndDate());
        Long postId = postService.propose(post);
        Post findPost = postService.findOneById(postId);

        tagPostService.save(new TagPost(tagService.findIdByName(request.getTopic()), findPost));
        tagPostService.save(new TagPost(tagService.findIdByName(request.getTarget()), findPost));

        request.getTag().forEach(m -> {
            Tag tag = new Tag(m.text);
            tagService.addTag(tag);
            tagPostService.save(new TagPost(tagService.findIdByName(m.text), findPost));
        });

        request.getLink().forEach(m -> {
            Link link = new Link(findPost, m.text);
            linkService.save(link);
        });

        return new CreatedPostResponse(true);
    }

    @Data
    static class CreatedPostRequest {
        private String title;
        private String proposer;
        private String content;
        private Integer targetAmount;
        private Date startDate;
        private Date endDate;
        private String topic;
        private String target;
        private List<CreatedPostRequest> tag;
        private List<CreatedPostRequest> link;
        private String id;
        private String text;
    }

    @Data
    static class CreatedPostResponse {
        private Boolean status;

        public CreatedPostResponse(Boolean status) {
            this.status = status;
        }
    }

    @GetMapping("fundraisings/{id}")
    public FindPostResponse findPost(@PathVariable("id") Long id) {
        Post findPost = postService.findOneById(id);

        FindPostResponse findPostResponse = new FindPostResponse(
                findPost.getTitle(), findPost.getProposer(), findPost.getContent(),
                findPost.getTargetAmount(), findPost.getStartDate(),findPost.getEndDate());
        return findPostResponse;
    }

    @Data
    @AllArgsConstructor
    static class FindPostResponse<T> {
        private String title;
        private String subTitle;
        private String content;
        private Integer targetAmount;
        private Date startDate;
        private Date endDate;
    }
}
