package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Comment;
import kakao.valuetogether.domain.Member;
import kakao.valuetogether.domain.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class CommentRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Comment comment) {
        em.persist(comment);
        return comment.getId();
    }

    public Long update(Comment comment, String newContent) {
        comment.editContent(newContent);
        em.persist(comment);
        return comment.getId();
    }

    public Long delete(Comment comment) {
        Long deletedCommentId = comment.getId();
        em.remove(comment);
        return deletedCommentId;
    }

    public void addLikes(Comment comment) {
        comment.addLikes();
        em.persist(comment);
    }

    public void removeLikes(Comment comment) {
        comment.minusLikes();
        em.persist(comment);
    }

    public Comment findById(Long id) {
        return em.find(Comment.class, id);
    }

    public List<Comment> findCommentByPost(Post post) {
        return em.createQuery("select c from Comment c where c.post = :post order by c.date", Comment.class)
                .setParameter("post", post)
                .getResultList();
    }

//    public List<Object> findComments(Post post) {
//        return em.createQuery("select c,l from Comment c inner join LikeDetail l on c.post = :post order by c.date")
//                .setParameter("post", post)
//                .getResultList();
//    }
}
