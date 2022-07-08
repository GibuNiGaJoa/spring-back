package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Member;
import kakao.valuetogether.domain.Post;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.Assert.*;
@SpringBootTest
public class PostRepositoryTest {
    @Autowired
    PostRepository postRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    public void save(){
        Member member = new Member("email", "pw", "name", "phone", "address", "gender", "nickname", "birthday");

        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.findById(saveId);

        Post post = new Post(findMember,"a","a","a","a",1000, new Date(2019,01,01), new Date(2020,10,1),true);
        Long postSaveId = postRepository.save(post);

        Post post1 = new Post(findMember,"a","a","a","a",1000, new Date(2019,01,01), new Date(2020,10,1),true);
        Long postSaveId1 = postRepository.save(post1);
    }
}