package kakao.valuetogether.api;

import kakao.valuetogether.dto.MemberRequestDTO;
import kakao.valuetogether.domain.Member;
import kakao.valuetogether.service.JwtService;
import kakao.valuetogether.dto.MemberResponseDTO;
import kakao.valuetogether.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    private final JwtService jwtService;

    @PostMapping("/join")
    public ResponseEntity<MemberResponseDTO> joinMember(@RequestBody @Valid MemberRequestDTO request) {
        return new ResponseEntity<>(memberService.join(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public TokenResponse loginMember(@RequestBody @Valid MemberRequestDTO request){
        Member findMember = memberService.login(request.getEmail(), request.getPw());

        String token = jwtService.createToken(findMember.getId());
        jwtService.parseJwtToken("Bearer " + token);

        TokenDataResponse tokenDataResponse = new TokenDataResponse(token);
        TokenResponse tokenResponse = new TokenResponse("200", "OK", tokenDataResponse.getToken(),true);

        return tokenResponse;
    }

    @GetMapping(value = "/checkToken")
    public TokenResponseNoData checkToken(@RequestHeader(value = "Authorization") String token) {
        jwtService.parseJwtToken(token);

        TokenResponseNoData tokenResponseNoData = new TokenResponseNoData("200", "success",true);
        return tokenResponseNoData;
    }

    @Data
    @AllArgsConstructor
    static class TokenResponse {
        private String code;
        private String msg;
        private String token;
        private Boolean status;
    }

    @Data
    @AllArgsConstructor
    static class TokenDataResponse {
        private String token;
    }

    @Data
    @AllArgsConstructor
    static class TokenResponseNoData<T> {
        private String code;
        private String msg;
        private Boolean status;
    }

    @PostMapping("/login/find_account")
    public ResponseEntity<MemberResponseDTO> findEmail(@RequestBody @Valid MemberRequestDTO request) {
        return new ResponseEntity<>(memberService.findEmailByPhone(request), HttpStatus.FOUND);
    }

    @PostMapping("/login/find_password")
    public TokenResponse verifyMember(@RequestBody @Valid MemberRequestDTO request) {
        Member findMember = memberService.validateMember(request.getEmail(), request.getPhone());

        String token = jwtService.createToken(findMember.getId());//토큰 생성
        jwtService.parseJwtToken("Bearer " + token);//토큰 검증

        TokenDataResponse tokenDataResponse = new TokenDataResponse(token);
        TokenResponse tokenResponse = new TokenResponse("200", "OK", tokenDataResponse.getToken(),true);

        return tokenResponse;
    }

    @PutMapping("/login/find_password/change_password")
    public ResponseEntity<Boolean> changePw(@RequestHeader(value = "Authorization") String token, @RequestBody @Valid MemberRequestDTO request) {
        Long memberId = jwtService.parseJwtToken("Bearer " + token);

        memberService.changePw(memberId, request.getPw());
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @DeleteMapping("member/{id}")
    public void deleteMember(@PathVariable("id") Long id) {
        memberService.deleteMember(id);
    }
}
