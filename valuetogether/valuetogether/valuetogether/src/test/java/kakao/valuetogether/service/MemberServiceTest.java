package kakao.valuetogether.service;

import kakao.valuetogether.domain.Member;
import kakao.valuetogether.repository.MemberRepository;
import org.junit.Assert;
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

import java.util.Optional;

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
    public void 폰번호로아이디찾기성공및예외(){
        Member member1 = new Member("email", "pw", "name", "phone", "address", "gender", "nickname", "birthday");

        Long saveId1 = memberRepository.save(member1);

        String findPhone = memberService.idFindPhone(member1.getPhone());

        assertThat(member1.getPhone()).isEqualTo(findPhone);

        try {
            memberService.idFindPhone("asdf"); // 예외 발생해야함.
        } catch (IllegalStateException e) {
            return;
        }
        Assert.fail("예외가 발생해야 한다."); // 이 메서드가 실행되면 테스트는 실패!
    }

//    @Test(expected = IllegalStateException.class)
//    @Transactional
//    @Rollback(value = false)
//    public void 폰번호로아이디찾기예외() throws Exception{
//        Member member1 = new Member("email", "pw", "name", "phone", "address", "gender", "nickname", "birthday");
//
//        Long saveId1 = memberRepository.save(member1);
//
//        memberService.idFindPhone("asdf");
//
//    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void 닉네임이름전화번호로아이디찾기성공및예외() {
        Member member1 = new Member("email", "pw", "name", "phone", "address", "gender", "nickname", "birthday");

        Long saveId1 = memberRepository.save(member1);

        String findEmail1 = memberService.idFindNicknameOrNameAndPhone("nickname", "name", "phone");
        String findEmail2 = memberService.idFindNicknameOrNameAndPhone(null, "name", "phone");
        String findEmail3 = memberService.idFindNicknameOrNameAndPhone("nickname", null, "phone");
        assertThat(findEmail1).isEqualTo(member1.getEmail());
        assertThat(findEmail2).isEqualTo(member1.getEmail());
        assertThat(findEmail3).isEqualTo(member1.getEmail());

        try {
            memberService.idFindNicknameOrNameAndPhone("ㅁ","ㅁ","phone"); // 예외 발생해야함.
        } catch (IllegalStateException e) {
            return;
        }
        Assert.fail("예외가 발생해야 한다."); // 이 메서드가 실행되면 테스트는 실패!
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void 회원검증및PW재설정() {
        Member member1 = new Member("email", "pw", "name", "phone", "address", "gender", "nickname", "birthday");
        Long saveId1 = memberRepository.save(member1);

        Member member2 = new Member("email1", "pw", "name", "phone", "address", "gender", "nickname", "birthday");
        Long saveId2 = memberRepository.save(member2);

        String password = "asdf";
        Long findId = memberService.validateMember("email", null);

        Member findMember = memberService.changePw(findId, password);

        assertEquals(findMember.getPw(),password);

        try {
            Long findId2 = memberService.validateMember("ema", "one");
            Member findMember2 = memberService.changePw(findId2, "15967");// 예외 발생해야함.

        } catch (IllegalStateException e) {
            return;
        }
        Assert.fail("예외가 발생해야 한다."); // 이 메서드가 실행되면 테스트는 실패!
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void 회원탈퇴() {
        Member member1 = new Member("email", "pw", "name", "phone", "address", "gender", "nickname", "birthday");
        Long saveId1 = memberRepository.save(member1);

        Member member2 = new Member("email1", "pw", "name", "phone", "address", "gender", "nickname", "birthday");
        Long saveId2 = memberRepository.save(member2);

        //Member findMember = memberRepository.findOne(saveId1);
        memberService.deleteMember(saveId1);

        assertTrue(Optional.ofNullable(memberRepository.findById(saveId1)).isEmpty());
    }

}