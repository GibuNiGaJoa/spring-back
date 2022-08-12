package kakao.valuetogether.api;

import kakao.valuetogether.domain.Post;
import kakao.valuetogether.domain.Tag;
import kakao.valuetogether.service.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController // @Controller + @ResponseBody가 이 어노테이션에 포함된다.
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagApiController {
    private final PostService postService;

    private final MemberService memberService;

    private final TagService tagService;

    private final TagPostService tagPostService;

    private final LinkService linkService;
    private final JwtService jwtService;


    //태그키워드로 게시글조회
    @RequestMapping(value = "/{tagName}",method = RequestMethod.GET)
    public PostResult searchPostByTag(@PathVariable("tagName") String tagName) {
        Tag findTag = tagService.findIdByFullName(tagName);
        List<Post> findPosts = tagPostService.findAllPostByTag(findTag);
        List<PostDto> postList = findPosts.stream()
                .map(m -> new PostDto(m.getId(), m.getImage(), m.getTitle(), m.getProposer(), m.getEndDate()))
                .collect(Collectors.toList());

        return new PostResult(postList);
    }

    @RequestMapping(value = "/{tagName}/", method = RequestMethod.GET)
    public PostResult searchPostByTagPhase(@PathVariable("tagName") String tagName,@RequestParam("phase") Long number) {
        Tag findTag = tagService.findIdByFullName(tagName);
        if (number == 2) {
            List<Post> findPosts = tagPostService.findNowPostByTag(findTag);
            List<PostDto> postList = findPosts.stream()
                    .map(m -> new PostDto(m.getId(), m.getImage(), m.getTitle(), m.getProposer(), m.getEndDate()))
                    .collect(Collectors.toList());

            return new PostResult(postList);

        }
        else {
            List<Post> findPosts = tagPostService.findEndPostByTag(findTag);
            List<PostDto> postList = findPosts.stream()
                    .map(m -> new PostDto(m.getId(), m.getImage(), m.getTitle(), m.getProposer(), m.getEndDate()))
                    .collect(Collectors.toList());

            return new PostResult(postList);
        }
    }

    @Data
    @AllArgsConstructor
    static class PostResult<T> {
        private T post;
    }

    @Data
    @AllArgsConstructor
    static class PostDto {
        private Long id;
        private String image;
        private String title;
        private String proposer;
        private Date endDate;
    }

}
