package kakao.valuetogether.service;

import kakao.valuetogether.dto.MemberRequestDTO;
import kakao.valuetogether.dto.MemberResponseDTO;
import kakao.valuetogether.domain.Member;
import kakao.valuetogether.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member findOne(Long id) {
        return memberRepository.findById(id);
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public MemberResponseDTO join(MemberRequestDTO request) {
        Member member = Member.builder()
                .email(request.getEmail())
                .pw(request.getPw())
                .name(request.getName())
                .phone(request.getPhone())
                .address(request.getAddress())
                .gender(request.getGender())
                .nickname(request.getNickname())
                .birthday(request.getBirthday()).build();

        validateDuplicateMember(member);

        MemberResponseDTO result = MemberResponseDTO.builder()
                .id(memberRepository.save(member))
                .build();
        return result;
    }

    public void validateDuplicateMember(Member member) {
        Optional<Member> result = memberRepository.findByEmail(member.getEmail());

        if(result.isEmpty())
            return;
        else
            throw new IllegalStateException("이미 존재하는 회원입니다.");
    }

    public Member login(String email, String pw) {
        Optional<Member> findMember = memberRepository.findByEmailAndPw(email, pw);
        if (findMember.isEmpty()) {
            throw new IllegalStateException("존재하지 않는 계정입니다.");
        } else {
            return findMember.get();
        }
    }

    public MemberResponseDTO findEmailByPhone(MemberRequestDTO request) {
        String email = memberRepository.findByPhone(request.getPhone()).
                orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다.")).getEmail();

        MemberResponseDTO result = MemberResponseDTO.builder()
                .email(email)
                .phone(request.getPhone())
                .build();

        return result;
    }

    //PW 재설정 시 회원 검증
    public Member validateMember(String email, String phone){
        Optional<Member> findMember = memberRepository.findByEP(email, phone);
        if (findMember.isEmpty()){
            throw new IllegalStateException("존재하지 않는 계정입니다.");
        }
        else {
            return findMember.get();
        }
    }

    //PW 재설정
    public void changePw(Long id, String pw) {
        Member findMember = memberRepository.findById(id);
        findMember.setPw(pw);
    }

    //회원탈퇴 (게시글 있을때는 어떻게 할지 고민필요)
    public void deleteMember(Long id) {
        memberRepository.delete(id);
    }

}