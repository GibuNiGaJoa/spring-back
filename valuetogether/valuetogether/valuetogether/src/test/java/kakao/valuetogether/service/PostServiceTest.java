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

    @Test
    public void save(){

        Member member1 = new Member("email3", "pw", "name", "phone", "address", "gender", "nickname", "birthday");
        Member member2 = new Member("email4", "pw", "name", "phone", "address", "gender", "nickname", "birthday");

        Long saveId1 = memberRepository.save(member1);

        Member findMember = memberRepository.findById(saveId1);
        Post post = new Post();
        post.setMember(findMember);
        post.setTitle("asdf");
        post.setSubTitle("asldkfh");
        post.setTargetAmount(1564);
        post.setContent("aslhfwlhfsd");
        post.setStartDate(new Date());
        post.setEndDate(new Date());

        Long postId = postService.propose(post);

    }
}