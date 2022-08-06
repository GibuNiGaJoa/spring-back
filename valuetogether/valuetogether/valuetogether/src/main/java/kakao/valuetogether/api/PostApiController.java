package kakao.valuetogether.api;

import kakao.valuetogether.domain.Tag;
import kakao.valuetogether.repository.TagRepository;
import kakao.valuetogether.service.JwtService;
import kakao.valuetogether.service.PostService;
import kakao.valuetogether.service.TagPostService;
import kakao.valuetogether.service.TagService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/fundraisings/propose/project")
    public ProposeResponse propose(@RequestHeader(value = "Authorization") String token){
        jwtService.parseJwtToken(token);
        List<Tag> topicList = tagService.findTopic();
        List<Tag> targetList = tagService.findTarget();
        List<TopicTargetDto> topicCollect = topicList.stream()
                .map(m -> new TopicTargetDto(m.getTagName()))
                .collect(Collectors.toList());
        List<TopicTargetDto> targetCollect = targetList.stream()
                .map(m -> new TopicTargetDto(m.getTagName()))
                .collect(Collectors.toList());
        return new ProposeResponse(topicCollect,targetCollect);
    }

    @Data
    @AllArgsConstructor
    static class ProposeResponse<T>{
        private T topic;
        private T target;
    }

    @Data
    @AllArgsConstructor
    static class TopicTargetDto {
        private String name;
    }
}
