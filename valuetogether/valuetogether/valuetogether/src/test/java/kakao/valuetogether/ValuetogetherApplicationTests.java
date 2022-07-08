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
    @Rollback(value = false)
    void test() {
        Member member = new Member("email", "pw", "name", "phone", "address", "gender", "nickname", "birthday");
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        Post post = new Post(findMember, "title", "subtitle", "artice", "imag", 10, new Date(2019, 01, 01), new Date(2019, 03, 03), true);
        Long postSavedId = postRepository.save(post);

        Post findPost = postRepository.find(postSavedId);

        Member result = findPost.getMember();

        Assertions.assertThat(member).isEqualTo(result);
    }

}
