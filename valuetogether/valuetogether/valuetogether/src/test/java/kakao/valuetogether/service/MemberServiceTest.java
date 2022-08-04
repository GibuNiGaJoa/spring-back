package kakao.valuetogether.service;

import kakao.valuetogether.domain.Member;
import kakao.valuetogether.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    //@Transactional
    //@Rollback(value = false)
    public void 회원가입성공() {
        Member member1 = new Member("email1", "pw", "name", "phone", "address", "gender", "nickname", "birthday");
        Member member2 = new Member("email2", "pw", "name", "phone", "address", "gender", "nickname", "birthday");

        Long saveId1 = memberRepository.save(member1);
        Long saveId2 = memberRepository.save(member2);

        Member member = new Member("email3", "pw", "name", "phone", "address", "gender", "nickname", "birthday");

        Long saveId = memberService.join(member);

    }

    @Test(expected = IllegalStateException.class)
    //@Transactional
    //@Rollback(value = false)
    public void 회원가입예외() {
        Member member1 = new Member("email4", "pw", "name", "phone", "address", "gender", "nickname", "birthday");
        Member member2 = new Member("email5", "pw", "name", "phone", "address", "gender", "nickname", "birthday");

        Long saveId1 = memberRepository.save(member1);
        Long saveId2 = memberRepository.save(member2);

        Member member = new Member("email5", "pw", "name", "phone", "address", "gender", "nickname", "birthday");

        Long saveId = memberService.join(member);
    }

    @Test
    //@Transactional
    //@Rollback(value = false)
    public void 폰번호로아이디찾기성공(){
        Member member1 = new Member("email6", "pw", "name", "phone", "address", "gender", "nickname", "birthday");

        Long saveId1 = memberRepository.save(member1);

        String findEmail = memberService.findIdByPhone(member1.getPhone());

        assertThat(member1.getEmail()).isEqualTo(findEmail);
    }

    @Test(expected = IllegalStateException.class)
    //@Transactional
    //@Rollback(value = false)
    public void 폰번호로아이디찾기예외(){
        Member member1 = new Member("email7", "pw", "name", "phone", "address", "gender", "nickname", "birthday");

        Long saveId1 = memberRepository.save(member1);

        memberService.findIdByPhone("asdf");

    }

    @Test
    //@Transactional
    //@Rollback(value = false)
    public void 닉네임이름전화번호로아이디찾기성공및예외() {
        Member member1 = new Member("email8", "pw", "name", "phone", "address", "gender", "nickname", "birthday");

        Long saveId1 = memberRepository.save(member1);

        String findEmail1 = memberService.findIdByNNP("nickname", "name", "phone");
        String findEmail2 = memberService.findIdByNNP(null, "name", "phone");
        String findEmail3 = memberService.findIdByNNP("nickname", null, "phone");
        assertThat(findEmail1).isEqualTo(member1.getEmail());
        assertThat(findEmail2).isEqualTo(member1.getEmail());
        assertThat(findEmail3).isEqualTo(member1.getEmail());

        try {
            memberService.findIdByNNP("ㅁ","ㅁ","phone"); // 예외 발생해야함.
        } catch (IllegalStateException e) {
            return;
        }
        Assert.fail("예외가 발생해야 한다."); // 이 메서드가 실행되면 테스트는 실패!
    }

    @Test
//    @Transactional
//    @Rollback(value = false)
    public void 회원검증및PW재설정() {
        Member member1 = new Member("email9", "pw", "name", "phone", "address", "gender", "nickname", "birthday");
        Long saveId1 = memberRepository.save(member1);

        Member member2 = new Member("email10", "pw", "name", "phone", "address", "gender", "nickname", "birthday");
        Long saveId2 = memberRepository.save(member2);

        String password = "asdf";
        Member findMember = memberService.validateMember("email9", "null");

        memberService.changePw(findMember.getId(), password);

        try {
            Member findMember2 = memberService.validateMember("ema", "one");
            memberService.changePw(findMember2.getId(), "15967");// 예외 발생해야함.

        } catch (IllegalStateException e) {
            return;
        }
        Assert.fail("예외가 발생해야 한다."); // 이 메서드가 실행되면 테스트는 실패!
    }

    @Test
    //@Transactional
    //@Rollback(value = false)
    public void 회원탈퇴() {
        Member member1 = new Member("email12", "pw", "name", "phone", "address", "gender", "nickname", "birthday");
        Long saveId1 = memberRepository.save(member1);

        Member member2 = new Member("email13", "pw", "name", "phone", "address", "gender", "nickname", "birthday");
        Long saveId2 = memberRepository.save(member2);

        //Member findMember = memberRepository.findOne(saveId1);
        memberService.deleteMember(saveId1);

        assertTrue(Optional.ofNullable(memberRepository.findById(saveId1)).isEmpty());
    }

}