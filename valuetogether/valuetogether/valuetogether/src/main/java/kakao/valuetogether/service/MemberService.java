package kakao.valuetogether.service;

import kakao.valuetogether.domain.Member;
import kakao.valuetogether.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member findOne(Long id) {
        Member findMember = memberRepository.findById(id);
        return findMember;
    }

    //회원가입
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    //중복 이메일(회원) 검증
    public void validateDuplicateMember(Member member) {
        Optional<Member> findMember = memberRepository.findByEmail(member.getEmail());
        findMember.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    //로그인
//    public boolean login(String email, String pw) {
//        Optional<Member> findMember = memberRepository.findByEmailAndPw(email, pw);
//        if(findMember.isEmpty())
//            return false;
//
//        return true;
//    }
    public Member login(String email, String pw) {
        Optional<Member> findMember = memberRepository.findByEmailAndPw(email, pw);
        if (findMember.isEmpty()) {
            throw new IllegalStateException("존재하지 않는 계정입니다.");
        } else {return findMember.get();}
    }

    //ID 찾기 첫번째 방법
    public String findIdByPhone(String phone) {
        Optional<Member> findPhone = memberRepository.findByPhone(phone);
        if(findPhone.isEmpty()){
            throw new IllegalStateException("존재하지 않는 번호입니다.");
        }
        else {
            return findPhone.get().getEmail();
        }
    }

    //ID 찾기 두번째 방법
    public String findIdByNNP(String nickname, String name, String phone) {
        Optional<Member> findMember = memberRepository.findByNNP(nickname, name, phone);
        if (findMember.isEmpty()){
            throw new IllegalStateException("존재하지 않는 계정입니다.");
        }
        else {
            return findMember.get().getEmail();
        }
    }

    //PW 재설정 시 회원 검증
    public Long validateMember(String email, String phone){
        Optional<Member> findMember = memberRepository.findByEP(email, phone);
        if (findMember.isEmpty()){
            throw new IllegalStateException("존재하지 않는 계정입니다.");
        }
        return findMember.get().getId();
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