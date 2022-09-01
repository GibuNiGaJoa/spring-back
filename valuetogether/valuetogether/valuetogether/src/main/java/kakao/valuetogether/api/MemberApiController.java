package kakao.valuetogether.api;

import kakao.valuetogether.domain.Member;
import kakao.valuetogether.service.JwtService;
import kakao.valuetogether.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
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

        memberService.join(member);
        return new CreatedMemberResponse(true);
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
        private Boolean status;

        public CreatedMemberResponse(Boolean status) {
            this.status = status;
        }
    }

    //로그인 + 토큰생성 api
    @PostMapping("/login")
    public TokenResponse loginMember(@RequestBody @Valid LoginMemberRequest request){
        Member findMember = memberService.login(request.getEmail(), request.getPw());

        String token = jwtService.createToken(findMember.getId());//토큰 생성
        jwtService.parseJwtToken("Bearer " + token);//토큰 검증

        TokenDataResponse tokenDataResponse = new TokenDataResponse(token);
        TokenResponse tokenResponse = new TokenResponse("200", "OK", tokenDataResponse.getToken(),true,findMember.getGender());

        return tokenResponse;
    }

    @Data
    static class LoginMemberRequest {
        private String email;
        private String pw;
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
        private String gender;
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

    //ID찾기
    //첫번째 방법(휴대폰번호)
    @PostMapping("/login/find_account_guide/first")
    public FindAccountByPhoneResponse findEmail(@RequestBody @Valid FindAccountByPhoneRequest request) {
        Member findMember = memberService.findIdByPhone(request.getPhone());
        return new FindAccountByPhoneResponse(findMember.getEmail(),findMember.getPhone());
    }

    @Data
    static class FindAccountByPhoneRequest {
        private String phone;
    }

    @Data
    static class FindAccountByPhoneResponse {

        private String email;
        private String phone;

        public FindAccountByPhoneResponse(String email, String phone) {
            this.email = email;
            this.phone = phone;
        }
    }

    //두번째 방법(닉네임 또는 이름과 휴대폰번호)
    @PostMapping("/login/find_account_guide/second")
    public FindAccountByNNPResponse findEmail(@RequestBody @Valid FindAccountByNNPRequest request) {
        Member findMember = memberService.findIdByNNP(request.getNicknameOrName(), request.getNicknameOrName(), request.getPhone());
        return new FindAccountByNNPResponse(findMember.getEmail(),findMember.getPhone());
    }

    @Data
    static class FindAccountByNNPRequest {
        private String nicknameOrName;
        private String phone;
    }

    @Data
    static class FindAccountByNNPResponse {

        private String email;
        private String phone;

        public FindAccountByNNPResponse(String email, String phone) {
            this.email = email;
            this.phone = phone;
        }
    }

    //회원검증 및 PW재설정
    //회원검증
    @PostMapping("/login/find_password")
    public TokenResponse verifyMember(@RequestBody @Valid FindPasswordByEPRequest request) {
        Member findMember = memberService.validateMember(request.getEmail(), request.getPhone());

        String token = jwtService.createToken(findMember.getId());//토큰 생성
        jwtService.parseJwtToken("Bearer " + token);//토큰 검증

        TokenDataResponse tokenDataResponse = new TokenDataResponse(token);
        TokenResponse tokenResponse = new TokenResponse("200", "OK", tokenDataResponse.getToken(),true,findMember.getGender());

        return tokenResponse;
    }

    @Data
    static class FindPasswordByEPRequest {
        private String email;
        private String phone;
    }

    //PW재설정
    @PutMapping("/login/find_password/change_password")
    public ChangePasswordResponse changePw(@RequestHeader(value = "Authorization") String token, @RequestBody @Valid ChangePasswordRequest request) {
        Long memberId = jwtService.parseJwtToken("Bearer " + token);//토큰 검증

        memberService.changePw(memberId, request.getPw());
        return new ChangePasswordResponse(true);
    }

    @Data
    static class ChangePasswordRequest {
        private String pw;
    }
    @Data
    static class ChangePasswordResponse {
        private Boolean status;

        public ChangePasswordResponse(Boolean status) {
            this.status = status;
        }
    }

    @DeleteMapping("member/{id}")
    public void deleteMember(@PathVariable("id") Long id) {
        memberService.deleteMember(id);
    }

    @Data
    static class DeleteResponse {
        private Boolean status;

        public DeleteResponse(Boolean status) {
            this.status = status;
        }
    }

}
