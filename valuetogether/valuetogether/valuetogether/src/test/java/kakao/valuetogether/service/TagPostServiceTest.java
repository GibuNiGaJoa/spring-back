package kakao.valuetogether.service;

import kakao.valuetogether.domain.Member;
import kakao.valuetogether.domain.Post;
import kakao.valuetogether.domain.Tag;
import kakao.valuetogether.domain.TagPost;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
//@Rollback(value = false)
public class TagPostServiceTest {

    @Autowired TagService tagService;

    @Autowired TagPostService tagPostService;

    @Autowired
    MemberService memberService;

    @Autowired PostService postService;

    @Test
    public void save() {
//        Member member = new Member("email3", "pw", "name", "phone", "address", "gender", "nickname", "birthday");
//
//        Long saveId = memberService.join(member);

//
        tagService.addTag(new Tag("모두의교육"));
        tagService.addTag(new Tag("기본생활지원"));
        tagService.addTag(new Tag("안정된일자리"));
        tagService.addTag(new Tag("건강한삶"));
        tagService.addTag(new Tag("인권평화와역사"));
        tagService.addTag(new Tag("동물"));
        tagService.addTag(new Tag("지역공동체"));
        tagService.addTag(new Tag("더나은사회"));
        tagService.addTag(new Tag("환경"));
        tagService.addTag(new Tag("아동|청소년"));
        tagService.addTag(new Tag("청년"));
        tagService.addTag(new Tag("여성"));
        tagService.addTag(new Tag("실버세대"));
        tagService.addTag(new Tag("장애인"));
        tagService.addTag(new Tag("이주민|다문화"));
        tagService.addTag(new Tag("지구촌"));
        tagService.addTag(new Tag("어려운이웃"));
        tagService.addTag(new Tag("우리사회"));
        tagService.addTag(new Tag("유기동물"));
        tagService.addTag(new Tag("야생동물"));
        Member findMember1 = memberService.findOne(1L);

        Post post1 = new Post(findMember1, "title123", "subtitle123", "content123", 10000, new Date(), new Date(),"asdfasf", false);

        postService.propose(post1);

        Tag tag = new Tag("기부니가좋을걸d");
        tagService.addTag(tag);
        Tag tag1 = tagService.findIdByFullName("기부니가좋을걸d");
        Tag tag2 = tagService.findIdByFullName("모두의교육");
        Tag tag3 = tagService.findIdByFullName("실버세대");
        TagPost tagPost1 = new TagPost(tag1, post1);
        tagPostService.save(tagPost1);
        TagPost tagPost2 = new TagPost(tag2, post1);
        tagPostService.save(tagPost2);
        TagPost tagPost3 = new TagPost(tag3, post1);
        tagPostService.save(tagPost3);

    }
}