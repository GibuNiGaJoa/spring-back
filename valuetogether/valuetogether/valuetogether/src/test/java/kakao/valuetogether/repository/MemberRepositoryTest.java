package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;

    @Test
    @Rollback(value = false)
    public void save() {
        Member member = new Member("email", "pw", "name", "phone", "address", "gender", "nickname", "birthday");

        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        Assertions.assertThat(member).isEqualTo(findMember);
    }
}
