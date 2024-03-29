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

    public void delete(Comment comment) {
        em.remove(comment);
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

//    public Post findPostByComment(Long id) {
//        return em.find(Post.class, id);
//    }

    public List<Comment> findCommentByPost(Post post) {
        return em.createQuery("select c from Comment c where c.post = :post order by c.date", Comment.class)
                .setParameter("post", post)
                .getResultList();
    }

    public List<Comment> findCommentsByMember(Member member) {
        return em.createQuery("select c from Comment c where c.member = :member order by c.date", Comment.class)
                .setParameter("member", member)
                .getResultList();
    }
}
