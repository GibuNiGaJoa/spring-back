package kakao.valuetogether.api;

import kakao.valuetogether.dto.MyPageResponseDTO;
import kakao.valuetogether.service.JwtService;
import kakao.valuetogether.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyPageApiController {

    private final MyPageService myPageService;
    private final JwtService jwtService;

    @GetMapping("/my")
    public ResponseEntity<MyPageResponseDTO> accessMyPage(@RequestHeader(value = "Authorization") String token) {
        Long memberId = jwtService.parseJwtToken("Bearer " + token);
        MyPageResponseDTO result = myPageService.accessMyPage(memberId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
