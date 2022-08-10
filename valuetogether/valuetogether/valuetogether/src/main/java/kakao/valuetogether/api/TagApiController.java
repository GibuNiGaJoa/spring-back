package kakao.valuetogether.api;

import kakao.valuetogether.domain.Post;
import kakao.valuetogether.domain.Tag;
import kakao.valuetogether.service.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController // @Controller + @ResponseBody가 이 어노테이션에 포함된다.
@RequiredArgsConstructor
public class TagApiController {
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
}
