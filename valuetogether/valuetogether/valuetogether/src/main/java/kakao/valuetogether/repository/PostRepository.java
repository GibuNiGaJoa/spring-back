package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PostRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Post post) {
        em.persist(post);
        return post.getId();
    }

    public Post findById(Long postId) {
        return em.find(Post.class, postId);
    }
}
