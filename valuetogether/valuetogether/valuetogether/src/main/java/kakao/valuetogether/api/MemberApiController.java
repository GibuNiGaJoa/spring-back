package kakao.valuetogether.api;

import kakao.valuetogether.domain.Member;
import kakao.valuetogether.service.JwtService;
import kakao.valuetogether.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    //-------------------------------여기까지 회원가입부분------------------


    //로그인 + 토큰생성 api
    @PostMapping("/login")
    public TokenResponse loginMember(@RequestBody @Valid LoginMemberRequest request){
        Member findMember = memberService.login(request.getEmail(), request.getPw());

        String token = jwtService.createToken(findMember.getId());//토큰 생성
        jwtService.parseJwtToken("Bearer " + token);//토큰 검증

        TokenDataResponse tokenDataResponse = new TokenDataResponse(token);
        TokenResponse tokenResponse = new TokenResponse("200", "OK", tokenDataResponse.getToken(),true);

        return tokenResponse;
    }

    //==토큰 인증 컨트롤러==//
    @GetMapping(value = "/checkToken")
    public TokenResponseNoData checkToken(@RequestHeader(value = "Authorization") String token) {
        Long memberId = jwtService.parseJwtToken("Bearer " + token);

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
    //--------------------------------------

    //-------------------------------여기까지 로그인부분------------------
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

    //-------------------------------여기까지 ID찾기부분------------------
    //회원검증 및 PW재설정
    //회원검증
    @PostMapping("/login/find_password")
    public TokenResponse verifyMember(@RequestBody @Valid FindPasswordByEPRequest request) {
        Member findMember = memberService.validateMember(request.getEmail(), request.getPhone());

        String token = jwtService.createToken(findMember.getId());//토큰 생성
        jwtService.parseJwtToken("Bearer " + token);//토큰 검증

        TokenDataResponse tokenDataResponse = new TokenDataResponse(token);
        TokenResponse tokenResponse = new TokenResponse("200", "OK", tokenDataResponse.getToken(),true);

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
    //-------------------------------여기까지 회원검증 및 PW재설정------------------
    //회원탈퇴
    @DeleteMapping("/delete")
    public DeleteResponse deleteMember(@RequestHeader(value = "Authorization") String token) {
        Long memberId = jwtService.parseJwtToken("Bearer " + token);//토큰 검증

        memberService.deleteMember(memberId);
        return new DeleteResponse(true);
    }

    @Data
    static class DeleteResponse {
        private Boolean status;

        public DeleteResponse(Boolean status) {
            this.status = status;
        }
    }

}
