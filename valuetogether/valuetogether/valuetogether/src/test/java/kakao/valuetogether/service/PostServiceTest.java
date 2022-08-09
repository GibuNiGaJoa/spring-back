package kakao.valuetogether.service;

import kakao.valuetogether.domain.*;
import kakao.valuetogether.domain.enums.Target;
import kakao.valuetogether.domain.enums.Topic;
import kakao.valuetogether.repository.*;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    @Autowired TagRepository tagRepository;
    @Autowired PostService postService;
    @Autowired TagPostRepository tagPostRepository;
    @Autowired MemberService memberService;

    @Test
    public void save(){

        Post post = new Post();
        post.setTitle("왜 안되냐고");
        post.setProposer("죽겠다..");
//        post.setTargetAmount(151565464);
        post.setContent("황재원 골드");
        post.setImage("asdfhwsdf");
        post.setEndDate(new Date());
        post.setStartDate(new Date());
        post.setTargetAmount(12312);
//        post.setStartDate(new Date());
//        post.setEndDate(new Date());
        Member findMember = memberService.findOne(1L);
        post.setMember(findMember);

        Long postId = postService.propose(post);

    }
}