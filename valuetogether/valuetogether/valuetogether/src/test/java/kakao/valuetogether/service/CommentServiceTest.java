package kakao.valuetogether.service;

import kakao.valuetogether.domain.*;
import kakao.valuetogether.repository.CommentRepository;
import kakao.valuetogether.repository.MemberRepository;
import kakao.valuetogether.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class CommentServiceTest {

    @Autowired MemberRepository memberRepository;
    @Autowired PostRepository postRepository;
    @Autowired CommentService commentService;
    @Autowired CommentRepository commentRepository;

    private Member findMember;
    private Post findPost;
    private Comment comment, findComment;
    private Long savedCommentId;

    @BeforeEach
    void beforeEach() {
        findMember = getFindMember();
        findPost = getFindPost(findMember);
        comment = createComment(findMember, findPost);
        savedCommentId = commentService.enrollComment(comment);
        findComment = commentRepository.findById(savedCommentId);
    }

    @Test
    public void enroll() {
        assertThat(comment).isEqualTo(findComment);
    }

    @Test
    public void edit() {
        String firstContent = findComment.getContent();

        Long editId = commentService.editComment(findComment, "this is new content");
        Comment editComment = commentRepository.findById(editId);
        String editContent = editComment.getContent();

        assertThat(firstContent).isNotEqualTo(editContent);
    }

    @Test
    public void remove() {
        commentService.removeComment(findComment);
    }

    @Test
    public void clickLike() {
        commentService.clickLike(findComment);
        Integer likes = findComment.getLikes();
        assertThat(likes).isEqualTo(1);

        commentService.clickLike(findComment);
        likes = findComment.getLikes();
        assertThat(likes).isEqualTo(2);
    }

    @Test
    public void disLike() {
        commentService.clickLike(findComment);
        commentService.disLike(findComment);
        Integer likes = findComment.getLikes();
        assertThat(likes).isEqualTo(0);

        commentService.clickLike(findComment);
        commentService.clickLike(findComment);
        commentService.disLike(findComment);
        likes = findComment.getLikes();
        assertThat(likes).isEqualTo(1);
    }
    // end of Test Methods

    public Member getFindMember() {
        Member member = new Member("email", "pw", "name", "111", "asdfasd", "asdf", "asfsa", "asdf");
        Long memberSavedId = memberRepository.save(member);
        return memberRepository.findById(memberSavedId);
    }

    public Post getFindPost(Member findMember) {
        Post post = new Post(findMember, "title", "subtitle", "article", "image", Topic.건강한삶, Target.아동ㅣ청소년, 100000, new Date(20, 07, 01), new Date(22, 07, 01), false);
        Long postSavedId = postRepository.save(post);
        return postRepository.findById(postSavedId);
    }

    public Comment createComment(Member findMember, Post findPost) {
        return new Comment(findMember, findPost, "content", new Date(2022, 6, 8), 0);
    }
}