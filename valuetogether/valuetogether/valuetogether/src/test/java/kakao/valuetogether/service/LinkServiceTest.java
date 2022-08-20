//package kakao.valuetogether.service;
//
//import kakao.valuetogether.domain.Link;
//import kakao.valuetogether.domain.Member;
//import kakao.valuetogether.domain.Post;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.sql.Timestamp;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Date;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Transactional
////@Rollback(value = false)
//public class LinkServiceTest {
//
//    @Autowired
//    LinkService linkService;
//
//    @Autowired
//    MemberService memberService;
//
//    @Autowired
//    PostService postService;
//
//    @Test
//    public void save() {
//        Member findMember1 = memberService.findOne(1L);
//
//        Post post1 = new Post(findMember1, "title123", "subtitle123", "content123", 10000, new Date(), new Date(), "aslfksf",false);
//
//        postService.propose(post1);
//        Link link = new Link();
//        linkService.save(new Link(post1, "asdfasdf"));
//    }
//}
