package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Comment;
import kakao.valuetogether.domain.Member;
import kakao.valuetogether.domain.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class CommentRepositoryTest {

    @Autowired CommentRepository commentRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired PostRepository postRepository;

    @Test
    public void save() {
        // given
        Member findMember = getFindMember();
        Post findPost = getFindPost(findMember);

        Comment comment = createComment(findMember, findPost);
        Long commentSavedId = saveComment(comment);

        // when
        Comment findComment = findComment(commentSavedId);

        // then
        assertThat(comment).isEqualTo(findComment);
    }

    @Test
    public void editComment() {
        // given
        String firstStr = "Content";
        String secondStr = "This is edit Content";

        Member findMember = getFindMember();
        Post findPost = getFindPost(findMember);

        Comment comment = createComment(findMember, findPost);
        Long commentSavedId = saveComment(comment);

        // when
        Comment findComment = findComment(commentSavedId);
        String firstContent = findComment.getContent();

        Long editCommentId = commentRepository.updateComment(findComment, secondStr);
        Comment editComment = commentRepository.find(editCommentId);
        String secondContent = editComment.getContent();

        // then
        assertThat(firstContent).isEqualTo(firstStr);
        assertThat(secondContent).isEqualTo(secondStr);
    }

    @Test
    public void addLikes() {
        // given
        Member findMember = getFindMember();
        Post findPost = getFindPost(findMember);

        Comment comment = createComment(findMember, findPost);
        Long commentSavedId = saveComment(comment);

        // when
        Comment findComment = findComment(commentSavedId);
        Integer firstLikes = findComment.getLikes();

        findComment.addLikes();

        Comment reFindComment = commentRepository.find(commentSavedId);
        Integer secondLikes = reFindComment.getLikes();

        // then
        assertThat(0).isEqualTo(firstLikes);
        assertThat(1).isEqualTo(secondLikes);
    }

    @Test
    public void minusLikes() {
        Member findMember = getFindMember();
        Post findPost = getFindPost(findMember);
        Comment comment = createComment(findMember, findPost);
        Long savedId = saveComment(comment);
        Comment findComment = findComment(savedId);

        Integer firstLikes = findComment.getLikes();

        commentRepository.addLikes(findComment);
        commentRepository.minusLikes(findComment);

        Integer secondLikes = findComment.getLikes();

        assertThat(firstLikes).isEqualTo(secondLikes);

        // 좋아요가 0인 상태에서 minusLieks 했을 경우
        boolean result = commentRepository.minusLikes(findComment);
        assertThat(result).isEqualTo(false);
    }

    @Test
    public void deleteComment() {
        // given
        Member findMember = getFindMember();
        Post findPost = getFindPost(findMember);

        // when
        Comment comment = createComment(findMember, findPost);
        Long commentSavedId = saveComment(comment);

        // then
        Comment findComment = findComment(commentSavedId);
        Long deleteComment = commentRepository.deleteComment(findComment);
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
        return new Comment(findMember, findPost, "content", new Date(11, 11, 11), 0);
    }

    public Long saveComment(Comment comment) {
        return commentRepository.saveComment(comment);
    }

    public Comment findComment(Long id) {
        return commentRepository.find(id);
    }

}
