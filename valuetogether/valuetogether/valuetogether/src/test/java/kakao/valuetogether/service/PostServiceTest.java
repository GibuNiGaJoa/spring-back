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
@Rollback(value = false)
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
        post.setTitle("asdfasfwe");
        post.setSubTitle("qfasdfq");
        post.setTargetAmount(1564);
        post.setContent("aslasdfqwfasfhfwlhfsd");
        post.setStartDate(new Date());
        post.setEndDate(new Date());
        Member findMember = memberService.findOne(1L);
        post.setMember(findMember);

        Long postId = postService.propose(post);

    }
}