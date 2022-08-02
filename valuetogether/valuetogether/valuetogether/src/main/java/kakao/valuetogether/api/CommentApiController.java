package kakao.valuetogether.api;

import kakao.valuetogether.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * TODO:
 * 0. 테스트용 API 개발 -> 프론트에서 백으로 제대로 값이 넘어오는지 확인
 * 1. 넘어온 데이터(String)를 base64만 잘라서 ./resources/static/images 에 저장.
 * 2. 해당 파일 URL 반환
 */
// String 받는 메소드 -> base64 추출하는 메소드 -> base64를 저장하고 URL 반환하는 메소드

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/test")
    public String test(@RequestParam(value = "img") String img) {
        return img;
    }
}