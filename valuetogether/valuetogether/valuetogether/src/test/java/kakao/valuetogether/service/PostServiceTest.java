package kakao.valuetogether.service;

import kakao.valuetogether.domain.*;
import kakao.valuetogether.domain.enums.Target;
import kakao.valuetogether.domain.enums.Topic;
import kakao.valuetogether.repository.LinkRepository;
import kakao.valuetogether.repository.MemberRepository;
import kakao.valuetogether.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
//@Rollback(value = false)
public class PostServiceTest {

    @Autowired MemberRepository memberRepository;
    @Autowired PostRepository postRepository;
    @Autowired LinkRepository linkRepository;
    @Autowired PostService postService;

    @Test
    public void 제안하기() {
        postService.basicSetting();
        Member member = Member.builder().email("hkim4410@naver.com").pw("pw").name("name").phone("0177239811").address("adress").gender("man").birthday("birthday").nickname("nickname").build();

        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.findById(saveId);

        Post post1 = new Post(findMember, "a", "a", "a", "a", Topic.건강한삶, Target.아동ㅣ청소년, 1000, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), true);
        Long postSaveId1 = postService.propose(post1);

        Link link1 = new Link("asdf");
        Link link2 = new Link("aslkjfhw");
        post1.addLink(link1);
        post1.addLink(link2);

        linkRepository.save(link1);
        linkRepository.save(link2);

        Post post2 = new Post(findMember, "a", "a", "a", "a", Topic.건강한삶, Target.아동ㅣ청소년, 1000, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), true);
        Long postSaveId2 = postService.propose(post2);

        Link link3 = new Link("asdf");
        Link link4 = new Link("aslkjfhw");

        post2.addLink(link3);
        post2.addLink(link4);

        linkRepository.save(link3);
        linkRepository.save(link4);

        Assertions.assertThat(post1).isEqualTo(postRepository.findOneById(postSaveId1));
        Assertions.assertThat(post2).isEqualTo(postRepository.findOneById(postSaveId2));
    }

//    @Test
//    public void 주제별대상별게시글검색하기() {
//        Member member = new Member("email", "pw", "name", "phone", "address", "gender", "nickname", "birthday");
//
//        Long saveId = memberRepository.save(member);
//        Member findMember = memberRepository.findById(saveId);
//
//        Post post1 = new Post(findMember, "a", "a", "a", "a", Topic.건강한삶, Target.아동ㅣ청소년, 1000, new Date(2019, 01, 01), new Date(2020, 10, 1), true);
//        Long postSaveId1 = postService.propose(post1);
//
//        Post post2 = new Post(findMember, "a", "a", "a", "a", Topic.건강한삶, Target.아동ㅣ청소년, 1000, new Date(2019, 01, 01), new Date(2020, 10, 1), true);
//        Long postSaveId2 = postService.propose(post2);
//
//        Post post3 = new Post(findMember, "a", "a", "a", "a", Topic.건강한삶, Target.아동ㅣ청소년, 1000, new Date(2019, 01, 01), new Date(2020, 10, 1), true);
//        Long postSaveId3 = postService.propose(post3);
//
//        List<Post> findPostByTopic = postService.searchByTopic(Topic.건강한삶);
//        assertEquals(3,findPostByTopic.size());
//
//        List<Post> findPostByTarget = postService.searchByTarget(Target.아동ㅣ청소년);
//        assertEquals(3,findPostByTarget.size());
//    }
}