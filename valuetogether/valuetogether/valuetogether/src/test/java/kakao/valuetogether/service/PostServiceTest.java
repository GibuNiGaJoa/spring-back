package kakao.valuetogether.service;

import kakao.valuetogether.domain.Member;
import kakao.valuetogether.domain.Post;
import kakao.valuetogether.repository.MemberRepository;
import kakao.valuetogether.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class PostServiceTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @Test
    @Transactional
    @Rollback(value = false)
    public void 제안하기() {
        Member member = new Member("email", "pw", "name", "phone", "address", "gender", "nickname", "birthday");

        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.findById(saveId);

        Post post = new Post(findMember,"a","a","a","a",1000, new Date(2019,01,01), new Date(2020,10,1),true);
        Long postSaveId = postService.propose(post);

        Post post1 = new Post(findMember,"a","a","a","a",1000, new Date(2019,01,01), new Date(2020,10,1),true);
        Long postSaveId1 = postService.propose(post1);

        Assertions.assertThat(post).isEqualTo(postRepository.findById(postSaveId));
        Assertions.assertThat(post1).isEqualTo(postRepository.findById(postSaveId1));
    }
}