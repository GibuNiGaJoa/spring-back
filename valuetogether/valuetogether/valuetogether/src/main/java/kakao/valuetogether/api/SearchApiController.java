package kakao.valuetogether.api;

import kakao.valuetogether.domain.Post;
import kakao.valuetogether.domain.Tag;
import kakao.valuetogether.service.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController // @Controller + @ResponseBody가 이 어노테이션에 포함된다.
@RequiredArgsConstructor
public class SearchApiController {

    private final PostService postService;

    private final MemberService memberService;

    private final TagService tagService;

    private final TagPostService tagPostService;

    private final LinkService linkService;
    private final JwtService jwtService;

    //검색시 랜덤으로 태그10개 제공하기
    @GetMapping("/search")
    public TagResult randomShowTag() {
        List<Tag> findTags = tagService.findTenTag();
        List<TagDto> tagList = findTags.stream()
                .map(m-> new TagDto(m.getTagName()))
                .collect(Collectors.toList());

        return new TagResult(tagList);
    }

    //검색어로 태그 및 게시글(제목+본문, 제목) 조회하기
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public TagAndPostResult SearchTagAndPost(@RequestParam("keyword") String keyword) {
        List<Tag> findTags = tagService.findTagByKeyword(keyword);
        List<TagDto> tagList = findTags.stream()
                .map(m-> new TagDto(m.getTagName()))
                .collect(Collectors.toList());

        List<Post> findPost1 = postService.findPostByKeyword(keyword);
        List<PostDto> postList_All = findPost1.stream()
                .map(m-> new PostDto(m.getId(), m.getImage(), m.getTitle(), m.getProposer(), m.getEndDate()))
                .collect(Collectors.toList());

        List<Post> findPost2 = postService.findPostByTileKeyword(keyword);
        List<PostDto> postList_Title = findPost2.stream()
                .map(m-> new PostDto(m.getId(), m.getImage(), m.getTitle(), m.getProposer(), m.getEndDate()))
                .collect(Collectors.toList());

        return new TagAndPostResult(tagList,postList_All,postList_Title);
    }

    @Data
    @AllArgsConstructor
    static class TagAndPostResult<T> {
        private T tag;
        private T post_all;
        private T post_title;
    }
    @Data
    @AllArgsConstructor
    static class TagResult<T>{
        private T tag;
    }
    @Data
    @AllArgsConstructor
    static class TagDto {
        private String name;
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
