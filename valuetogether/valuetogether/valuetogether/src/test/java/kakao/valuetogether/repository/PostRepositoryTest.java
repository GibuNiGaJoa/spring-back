package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Member;
import kakao.valuetogether.domain.Post;
import kakao.valuetogether.domain.Tag;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@SpringBootTest
@Transactional
//@Rollback(value = false)
public class PostRepositoryTest {

    @Autowired PostRepository postRepository;
    @Autowired MemberRepository memberRepository;

    @Autowired TagRepository tagRepository;


//    @BeforeEach
//    public void beforeEach() {
//        Member member = new Member("email", "pw", "name", "01077239811", "address", "man", "nickname", "birthday");
//        Long savedMemberId = memberRepository.save(member);
//        findMember = memberRepository.findById(savedMemberId);
//
//        Post post = new Post(findMember, "title", "subtitle", "article", "static/image", Topic.건강한삶, Target.실버세대, 10000, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), false);
//        Long savedPostId = postRepository.save(post);
//        savedPost = postRepository.findOneById(savedPostId);
//    }
//
//    @Test
//    public void save() {
//        Post post = new Post(findMember, "title", "subtitle", "article", "static/image", Topic.건강한삶, Target.실버세대, 10000, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), false);
//        Long postSaveId = postRepository.save(post);
//
//        Post post1 = new Post(findMember, "title", "subtitle", "article", "static/image", Topic.건강한삶, Target.실버세대, 10000, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), false);
//        Long postSaveId1 = postRepository.save(post1);
//    }
//
//    @Test
//    public void findOneById() {
//        Post post = new Post(findMember, "title", "subtitle", "article", "static/image", Topic.건강한삶, Target.실버세대, 10000, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), false);
//        Long savedId = postRepository.save(post);
//        Post findPost = postRepository.findOneById(savedId);
//
//        Assertions.assertThat(post).isEqualTo(findPost);
//    }

}