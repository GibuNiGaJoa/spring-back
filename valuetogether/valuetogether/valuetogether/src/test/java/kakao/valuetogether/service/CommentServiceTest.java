package kakao.valuetogether.service;

import kakao.valuetogether.domain.Comment;
import kakao.valuetogether.domain.Member;
import kakao.valuetogether.domain.Post;
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
        return memberRepository.find(memberSavedId);
    }

    public Post getFindPost(Member findMember) {
        Post post = new Post(findMember, "title", "subtitle", "article", "img", 100, new Date(1020, 12, 12), new Date(231, 12, 12), true);
        Long postSavedId = postRepository.save(post);
        return postRepository.find(postSavedId);
    }

    public Comment createComment(Member findMember, Post findPost) {
        return new Comment(findMember, findPost, "content", new Date(2022, 6, 8), 0);
    }

    public Comment findComment(Long id) {
        return commentRepository.find(id);
    }
}