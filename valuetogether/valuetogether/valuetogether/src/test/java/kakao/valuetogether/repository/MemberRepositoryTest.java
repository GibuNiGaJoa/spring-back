//package kakao.valuetogether.repository;
//
//import kakao.valuetogether.domain.Member;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//@Transactional
//public class MemberRepositoryTest {
//    @Autowired MemberRepository memberRepository;
//
//    @Test
//    //@Rollback(value = false)
//    public void save() {
//        Member member = Member.builder().email("hkim4410@naver.com").pw("pw").name("name").phone("0177239811").address("adress").gender("man").birthday("birthday").nickname("nickname").build();
//
//        Long savedId = memberRepository.save(member);
//        Member findMember = memberRepository.findById(savedId);
//
//        Assertions.assertThat(member).isEqualTo(findMember);
//    }
//}
