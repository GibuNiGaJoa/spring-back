//package kakao.valuetogether.repository;
//
//import kakao.valuetogether.domain.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.sql.Timestamp;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.fail;
//
//@SpringBootTest
//@Transactional
////@Rollback(value = false)
//public class CommentRepositoryTest {
//
//    @Autowired CommentRepository commentRepository;
//    @Autowired MemberRepository memberRepository;
//    @Autowired PostRepository postRepository;
//
//    private Member findMember;
//    private Post findPost;
//    private Comment comment, findComment;
//    private Long savedCommentId;
//
//    @BeforeEach
//    public void setUp() {
//        findMember = getFindMember();
//        findPost = getFindPost(findMember);
//        comment = createComment(findMember, findPost);
//        savedCommentId = saveComment(comment);
//        findComment = findComment(savedCommentId);
//    }
//
//    @Test
//    public void save() {
//        assertThat(comment).isEqualTo(findComment);
//    }
//
//    @Test
//    public void updateComment() {
//        String firstContent = findComment.getContent();
//
//        String secondStr = "This is edit content";
//        Long editCommentId = commentRepository.update(findComment, secondStr);
//        Comment editComment = commentRepository.findById(editCommentId);
//        String secondContent = editComment.getContent();
//
//        assertThat(firstContent).isNotEqualTo(secondStr);
//        assertThat(secondContent).isEqualTo(secondStr);
//    }
//
//    @Test
//    public void deleteComment() {
//        Member findMember = getFindMember();
//        Post findPost = getFindPost(findMember);
//
//        Comment comment = createComment(findMember, findPost);
//        Long commentSavedId = saveComment(comment);
//
//        Comment findComment = findComment(commentSavedId);
//        Long deleteComment = commentRepository.delete(findComment);
//    }
//
//    @Test
//    public void addLikes() {
//        Integer firstLikes = findComment.getLikes();
//
//        findComment.addLikes();
//
//        Comment reFindComment = commentRepository.findById(savedCommentId);
//        Integer secondLikes = reFindComment.getLikes();
//
//        // then
//        assertThat(0).isEqualTo(firstLikes);
//        assertThat(1).isEqualTo(secondLikes);
//    }
//
//    @Test
//    public void minusLikes() {
//        Integer firstLikes = findComment.getLikes();
//
//        commentRepository.addLikes(findComment);
//        commentRepository.removeLikes(findComment);
//
//        Integer secondLikes = findComment.getLikes();
//
//        assertThat(firstLikes).isEqualTo(secondLikes);
//    }
//
//    @Test
//    public void findByPost() {
//        List<Comment> commentByPost = commentRepository.findCommentByPost(findPost);
//    }
//
////    @Test
////    public void findAllCommentsByPost() {
////        Comment comment = new Comment(findMember, findPost, "content", Timestamp.valueOf(LocalDateTime.now()), 1);
////        Comment comment1 = new Comment(findMember, findPost, "content1", Timestamp.valueOf(LocalDateTime.now()), 1);
////        Comment comment2 = new Comment(findMember, findPost, "content2", Timestamp.valueOf(LocalDateTime.now()), 1);
////
////        commentRepository.save(comment);
////        commentRepository.save(comment1);
////        commentRepository.save(comment2);
////
////        List<Comment> allCommentsByPost = (ArrayList)commentRepository.findAllCommentsByPost(findPost);
////
////        if(allCommentsByPost.size() == 0) fail("list 사이즈가 0. 실패.");
////        allCommentsByPost.forEach(co -> {
////            System.out.println("co.toString() = " + co.toString());
////        });
////    }
////
////    @Test
////    public void findAllCommentsByMember() {
////        Comment comment = new Comment(findMember, findPost, "content", Timestamp.valueOf(LocalDateTime.now()), 1);
////        Comment comment1 = new Comment(findMember, findPost, "content1", Timestamp.valueOf(LocalDateTime.now()), 1);
////        Comment comment2 = new Comment(findMember, findPost, "content2", Timestamp.valueOf(LocalDateTime.now()), 1);
////
////        commentRepository.save(comment);
////        commentRepository.save(comment1);
////        commentRepository.save(comment2);
////
////        List<Comment> allCommentsByMember = commentRepository.findAllCommentsByMember(findMember);
////        if(allCommentsByMember.size() == 0) fail("list 사이즈가 0. 실패.");
////        for (Comment co : allCommentsByMember)
////            System.out.println("co.toString() = " + co.toString());
////    }
//    // end of Test Methods
//
//    public Member getFindMember() {
//        Member member = Member.builder().email("hkim4410@naver.com").pw("pw").name("name").phone("0177239811").address("adress").gender("man").birthday("birthday").nickname("nickname").build();
//        Long memberSavedId = memberRepository.save(member);
//        return memberRepository.findById(memberSavedId);
//    }
//
//    public Post getFindPost(Member findMember) {
//        Post post = new Post(findMember, "title", "proposer", "content", 1000, new Date(1000), new Date(2000), "image", false);
//        Long postSavedId = postRepository.save(post);
//        return postRepository.findOneById(postSavedId);
//    }
//
//    public Comment createComment(Member findMember, Post findPost) {
//        return new Comment(findMember, findPost, "content", Timestamp.valueOf(LocalDateTime.now()), 0);
//    }
//
//    public Long saveComment(Comment comment) {
//        return commentRepository.save(comment);
//    }
//
//    public Comment findComment(Long id) {
//        return commentRepository.findById(id);
//    }
//
//}