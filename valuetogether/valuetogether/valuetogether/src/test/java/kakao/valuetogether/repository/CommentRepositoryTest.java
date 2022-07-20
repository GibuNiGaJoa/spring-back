package kakao.valuetogether.repository;

import kakao.valuetogether.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class CommentRepositoryTest {

    @Autowired CommentRepository commentRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired PostRepository postRepository;

    private Member findMember;
    private Post findPost;
    private Comment comment, findComment;
    private Long savedCommentId;

    @BeforeEach
    void beforeEach() {
        findMember = getFindMember();
        findPost = getFindPost(findMember);
        comment = createComment(findMember, findPost);
        savedCommentId = saveComment(comment);
        findComment = findComment(savedCommentId);
    }

    @Test
    public void save() {
        assertThat(comment).isEqualTo(findComment);
    }

    @Test
    public void updateComment() {
        String firstContent = findComment.getContent();

        String secondStr = "This is edit content";
        Long editCommentId = commentRepository.updateComment(findComment, secondStr);
        Comment editComment = commentRepository.findById(editCommentId);
        String secondContent = editComment.getContent();

        assertThat(firstContent).isNotEqualTo(secondStr);
        assertThat(secondContent).isEqualTo(secondStr);
    }

    @Test
    public void deleteComment() {
        Member findMember = getFindMember();
        Post findPost = getFindPost(findMember);

        Comment comment = createComment(findMember, findPost);
        Long commentSavedId = saveComment(comment);

        Comment findComment = findComment(commentSavedId);
        Long deleteComment = commentRepository.deleteComment(findComment);
    }

    @Test
    public void addLikes() {
        Integer firstLikes = findComment.getLikes();

        findComment.addLikes();

        Comment reFindComment = commentRepository.findById(savedCommentId);
        Integer secondLikes = reFindComment.getLikes();

        // then
        assertThat(0).isEqualTo(firstLikes);
        assertThat(1).isEqualTo(secondLikes);
    }

    @Test
    public void minusLikes() {
        Integer firstLikes = findComment.getLikes();

        commentRepository.addLikes(findComment);
        commentRepository.minusLikes(findComment);

        Integer secondLikes = findComment.getLikes();

        assertThat(firstLikes).isEqualTo(secondLikes);
    }

    @Test
    public void findByPost() {
        Comment byPost = commentRepository.findByPost(findPost);
        assertThat(byPost).isEqualTo(findComment);
    }
    // end of Test Methods

    public Member getFindMember() {
        Member member = new Member("email", "pw", "name", "111", "asdfasd", "asdf", "asfsa", "asdf");
        Long memberSavedId = memberRepository.save(member);
        return memberRepository.findById(memberSavedId);
    }

    public Post getFindPost(Member findMember) {
        Post post = new Post(findMember, "title", "subTitle", "article", "image", Topic.건강한삶, Target.실버세대, 100000, new Date(22, 7, 11), new Date(22, 8, 31), false);
        Long postSavedId = postRepository.save(post);
        return postRepository.findById(postSavedId);
    }

    public Comment createComment(Member findMember, Post findPost) {
        return new Comment(findMember, findPost, "content", new Date(11, 11, 11), 0);
    }

    public Long saveComment(Comment comment) {
        return commentRepository.saveComment(comment);
    }

    public Comment findComment(Long id) {
        return commentRepository.findById(id);
    }

}
