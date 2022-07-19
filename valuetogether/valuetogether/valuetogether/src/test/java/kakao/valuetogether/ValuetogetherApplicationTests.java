package kakao.valuetogether;

import kakao.valuetogether.domain.Member;
import kakao.valuetogether.domain.Post;
import kakao.valuetogether.repository.MemberRepository;
import kakao.valuetogether.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@SpringBootTest
@Transactional
class ValuetogetherApplicationTests {

    @Autowired PostRepository postRepository;
    @Autowired MemberRepository memberRepository;

    @Test
    void test() {
    }

}
