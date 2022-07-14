package kakao.valuetogether.api;

import kakao.valuetogether.domain.Member;
import kakao.valuetogether.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Column;
import javax.validation.Valid;

@RestController // @Controller + @ResponseBody가 이 어노테이션에 포함된다.
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    //회원가입 api
    @PostMapping("/api/login/create_account")
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
    @GetMapping("/api/login")
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
}
