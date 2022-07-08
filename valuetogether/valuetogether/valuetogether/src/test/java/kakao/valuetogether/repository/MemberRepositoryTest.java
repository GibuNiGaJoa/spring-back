package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Member;
import kakao.valuetogether.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    public void save() {
        Member member = new Member("email", "pw", "name", "phone", "address", "gender", "nickname", "birthday");

        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.findById(savedId);

        Assertions.assertThat(member).isEqualTo(findMember);
    }
}
