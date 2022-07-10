package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Post;
import kakao.valuetogether.domain.Target;
import kakao.valuetogether.domain.Topic;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Post post) {
        em.persist(post);
        return post.getId();
    }

    //게시글 하나 조회하기
    public Post findById(Long postId) {
        return em.find(Post.class, postId);
    }

    //주제별로 게시글 모두 조회하기
    public List<Post> findAllByTopic(Topic topic) {
        return em.createQuery("select p from Post p where p.topic = :topic")
                .setParameter("topic", topic)
                .getResultList();
    }

    //대상별로 게시글 모두 조회하기
    public List<Post> findAllByTarget(Target target) {
        return em.createQuery("select p from Post p where p.target = :target")
                .setParameter("target", target)
                .getResultList();
    }
}
