package kakao.valuetogether.service;

import kakao.valuetogether.domain.Member;
import kakao.valuetogether.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    MemberRepository memberRepository;
    //회원가입
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    //중복 이메일(회원) 검증
    public void validateDuplicateMember(Member member) {
        Optional<Member> findMembers = memberRepository.findByEmail(member.getEmail());
        findMembers.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    //ID 찾기
    public String idFindFirst(String phone) {
        Optional<Member> findPhones = memberRepository.findByPhone(phone);
        if(findPhones.isEmpty()){
            throw new IllegalStateException("존재하지 않는 번호입니다.");
        }
        else {
            return findPhones.get().getPhone();
        }
    }

    public String idFindSecond(String nickname, String name, String phone) {
        Optional<Member> findMembers = memberRepository.findByNNP(nickname, name, phone);
        if (findMembers.isEmpty()){
            throw new IllegalStateException("존재하지 않는 아이디입니다.");
        }
        else {
            return findMembers.get().getEmail();
        }
    }

}
