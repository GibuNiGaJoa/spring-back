package kakao.valuetogether.api;

import kakao.valuetogether.domain.Member;
import kakao.valuetogether.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import javax.validation.Valid;

@RestController // @Controller + @ResponseBody가 이 어노테이션에 포함된다.
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

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

    //로그인 api
    @GetMapping("/login")
    public LoginMemberResponse loginMember(@RequestBody @Valid LoginMemberRequest request) {

        Long id = memberService.login(request.getEmail(), request.getPw());
        return new LoginMemberResponse(id);
    }

    @Data
    static class LoginMemberRequest {
        private String email;
        private String pw;
    }

    @Data
    static class LoginMemberResponse {
        private Long id;

        public LoginMemberResponse(Long id) {
            this.id = id;
        }
    }

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
        Long id = memberService.validateMember(request.getEmail(), request.getPhone());
        return new FindPasswordByEPResponse(id);
    }

    @Data
    static class FindPasswordByEPRequest {
        private String email;
        private String phone;
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
