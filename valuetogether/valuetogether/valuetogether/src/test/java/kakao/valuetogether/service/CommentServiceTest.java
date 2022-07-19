package kakao.valuetogether.service;

import kakao.valuetogether.domain.*;
import kakao.valuetogether.repository.CommentRepository;
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
@Rollback(value = false)
public class CommentServiceTest {

    @Autowired MemberRepository memberRepository;
    @Autowired PostRepository postRepository;
    @Autowired CommentService commentService;
    @Autowired CommentRepository commentRepository;

    @Test
    public void enroll() {
        Member findMember = getFindMember();
        Post findPost = getFindPost(findMember);
        Comment comment = createComment(findMember, findPost);

        Long savedId = commentService.enrollComment(comment);

        Comment findComment = findComment(savedId);

        Assertions.assertThat(comment).isEqualTo(findComment);
    }

    public Member getFindMember() {
        Member member = new Member("email", "pw", "name", "111", "asdfasd", "asdf", "asfsa", "asdf");
        Long memberSavedId = memberRepository.save(member);
        return memberRepository.findById(memberSavedId);
    }

    public Post getFindPost(Member findMember) {
        Post post = new Post(findMember, "title", "subtitle", "article", "img", Topic.건강한삶, Target.실버세대,100, new Date(2019-11-11) , new Date(2019-11-11), true);
        Long postSavedId = postRepository.save(post);
        return postRepository.findById(postSavedId);
    }

    public Comment createComment(Member findMember, Post findPost) {
        return new Comment(findMember, findPost, "content", new Date(2022, 6, 8), 0);
    }

    public Comment findComment(Long id) {
        return commentRepository.find(id);
    }
}