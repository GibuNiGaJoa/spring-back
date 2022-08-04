package kakao.valuetogether.api;

import io.jsonwebtoken.Claims;
import kakao.valuetogether.domain.Member;
import kakao.valuetogether.service.JwtService;
import kakao.valuetogether.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController // @Controller + @ResponseBody가 이 어노테이션에 포함된다.
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    private final JwtService jwtService;


    //회원가입 api
    @PostMapping("/login/create_account")
    public CreatedMemberResponse saveMember(@RequestBody @Valid CreatedMemberRequest request) {

        Member member = new Member();
        member.setEmail(request.getEmail());
        member.setPw(request.getPw());
        member.setName(request.getName());
        member.setPhone(request.getPhone());
        member.setAddress(request.getAddress());
        member.setGender(request.getGender());
        member.setNickname(request.getNickname());
        member.setBirthday(request.getBirthday());

        Long id = memberService.join(member);
        return new CreatedMemberResponse(id);
    }

    @Data
    static class CreatedMemberRequest {
        private String email;

        private String pw;

        private String name;

        private String phone;

        private String address;

        private String gender;

        private String nickname;

        private String birthday;
    }

    @Data
    static class CreatedMemberResponse {
        private Long id;

        public CreatedMemberResponse(Long id) {
            this.id = id;
        }
    }
    //-------------------------------여기까지 회원가입부분------------------


    //로그인시 토큰생성 api
    @PostMapping("/login")
    public TokenResponse loginMember(@RequestBody @Valid LoginMemberRequest request) throws Exception{
        Member findMember = memberService.login(request.getEmail(), request.getPw());

        String token = jwtService.createToken(findMember.getId());//토큰 생성
        Long memberId = jwtService.parseJwtToken("Bearer " + token);//토큰 검증

        TokenDataResponse tokenDataResponse = new TokenDataResponse(token);
        TokenResponse tokenResponse = new TokenResponse("200", "OK", tokenDataResponse.getToken(),true);

        return tokenResponse;
    }

    //==토큰 인증 컨트롤러==//
    @GetMapping(value = "/checkToken")
    public TokenResponseNoData checkToken(@RequestHeader(value = "Authorization") String token) throws Exception {
        Long memberId = jwtService.parseJwtToken(token);

        TokenResponseNoData tokenResponseNoData = new TokenResponseNoData("200", "success",true);
        return tokenResponseNoData;
    }

    @Data
    static class LoginMemberRequest {
        private String email;
        private String pw;
    }

    @Data
    @AllArgsConstructor
    static class TokenResponse {
        private String code;
        private String msg;
        private String token;
        private Boolean status;
    }

    //==Response DTO==//
    @Data
    @AllArgsConstructor
    static class TokenDataResponse {
        private String token;
    }

    //==Response DTO==//
    @Data
    @AllArgsConstructor
    static class TokenResponseNoData<T> {

        private String code;
        private String msg;
        private Boolean status;
    }
    //--------------------------------------

    //-------------------------------여기까지 로그인부분------------------
    //ID찾기
    //첫번째 방법(휴대폰번호)
    @GetMapping("/login/find_account_guide/first")
    public FindAccountByPhoneResponse findEmail(@RequestBody @Valid FindAccountByPhoneRequest request) {
        String email = memberService.findIdByPhone(request.getPhone());
        return new FindAccountByPhoneResponse(email);
    }

    @Data
    static class FindAccountByPhoneRequest {
        private String phone;
    }

    @Data
    static class FindAccountByPhoneResponse {

        private String email;

        public FindAccountByPhoneResponse(String email) {
            this.email = email;
        }
    }

    //두번째 방법(닉네임 또는 이름과 휴대폰번호)
    @GetMapping("/login/find_account_guide/second")
    public FindAccountByNNPResponse findEmail(@RequestBody @Valid FindAccountByNNPRequest request) {
        String email = memberService.findIdByNNP(request.getNickname(), request.getName(), request.getPhone());
        return new FindAccountByNNPResponse(email);
    }

    @Data
    static class FindAccountByNNPRequest {
        private String nickname;
        private String name;
        private String phone;
    }

    @Data
    static class FindAccountByNNPResponse {

        private String email;

        public FindAccountByNNPResponse(String email) {
            this.email = email;
        }
    }

    //-------------------------------여기까지 ID찾기부분------------------
    //회원검증 및 PW재설정
    //회원검증
    @GetMapping("/login/find_password")
    public FindPasswordByEPResponse verifyMember(@RequestBody @Valid FindPasswordByEPRequest request) {
        Long id = memberService.validateMember(request.getEmailOrPhone(), request.getEmailOrPhone());
        return new FindPasswordByEPResponse(id);
    }

    @Data
    static class FindPasswordByEPRequest {
        private String emailOrPhone;
    }
    @Data
    static class FindPasswordByEPResponse {
        private Long id;

        public FindPasswordByEPResponse(Long id) {
            this.id = id;
        }
    }

    //PW재설정
    @PutMapping("/login/find_password/{id}")
    public ChangePasswordResponse changePw(@PathVariable("id") Long id, @RequestBody @Valid ChangePasswordRequest request) {
        memberService.changePw(id, request.getPw());
        Member findMember = memberService.findOne(id);
        return new ChangePasswordResponse(findMember.getId(),findMember.getPw());
    }

    @Data
    static class ChangePasswordRequest {
        private String pw;
    }
    @Data
    static class ChangePasswordResponse {
        private Long id;
        private String pw;

        public ChangePasswordResponse(Long id, String pw) {
            this.id = id;
            this.pw = pw;
        }
    }
    //-------------------------------여기까지 회원검증 및 PW재설정------------------
    //회원탈퇴
    @DeleteMapping("member/{id}")
    public void deleteMember(@PathVariable("id") Long id) {
        memberService.deleteMember(id);
    }
}
