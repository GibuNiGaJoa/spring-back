package kakao.valuetogether.service;

import kakao.valuetogether.domain.Member;
import kakao.valuetogether.repository.MemberRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;


    @Test
    @Transactional
    @Rollback(value = false)
    public void 회원가입성공() {
        Member member1 = new Member("email", "pw", "name", "phone", "address", "gender", "nickname", "birthday");
        Member member2 = new Member("email123", "pw", "name", "phone", "address", "gender", "nickname", "birthday");

        Long saveId1 = memberRepository.save(member1);
        Long saveId2 = memberRepository.save(member2);

        Member member = new Member("email3", "pw", "name", "phone", "address", "gender", "nickname", "birthday");

        Long saveId = memberService.join(member);

    }

    @Test(expected = IllegalStateException.class)
    @Transactional
    @Rollback(value = false)
    public void 회원가입예외() {
        Member member1 = new Member("email", "pw", "name", "phone", "address", "gender", "nickname", "birthday");
        Member member2 = new Member("email123", "pw", "name", "phone", "address", "gender", "nickname", "birthday");

        Long saveId1 = memberRepository.save(member1);
        Long saveId2 = memberRepository.save(member2);

        Member member = new Member("email", "pw", "name", "phone", "address", "gender", "nickname", "birthday");

        Long saveId = memberService.join(member);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void 폰번호로아이디찾기성공(){
        Member member1 = new Member("email", "pw", "name", "phone", "address", "gender", "nickname", "birthday");

        Long saveId1 = memberRepository.save(member1);

        Member findMember = memberRepository.findOne(saveId1);

        String findPhone = memberService.idFindFirst(findMember.getPhone());

        assertThat(member1.getPhone()).isEqualTo(findPhone);
    }

    @Test(expected = IllegalStateException.class)
    @Transactional
    @Rollback(value = false)
    public void 폰번호로아이디찾기예외() throws Exception{
        Member member1 = new Member("email", "pw", "name", "phone", "address", "gender", "nickname", "birthday");

        Long saveId1 = memberRepository.save(member1);

        String findPhone = memberService.idFindFirst("asdf");
    }



}