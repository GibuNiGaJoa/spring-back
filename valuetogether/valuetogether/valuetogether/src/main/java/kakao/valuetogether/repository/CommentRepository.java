package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CommentRepository {

    @PersistenceContext
    private EntityManager em;

    public Long saveComment(Comment comment) {
        em.persist(comment);
        return comment.getId();
    }

    public Long updateComment(Comment comment, String content) {
        comment.editContent(content);
        em.persist(comment);
        return comment.getId();
    }

    public Long deleteComment(Comment comment) {
        Long deletedCommentId = comment.getId();
        em.remove(comment);
        return deletedCommentId;
    }

    public void addLikes(Comment comment) {
        comment.addLikes();
        em.persist(comment);
    }

    public void minusLikes(Comment comment) {
        comment.minusLikes();
        em.persist(comment);
    }

    public Comment find(Long id) {
        return em.find(Comment.class, id);
    }
}
