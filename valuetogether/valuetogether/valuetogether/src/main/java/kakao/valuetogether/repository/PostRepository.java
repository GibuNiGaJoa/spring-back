package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Post;
import kakao.valuetogether.domain.enums.Target;
import kakao.valuetogether.domain.enums.Topic;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PostRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Post post) {
        em.persist(post);
        return post.getId();
    }

    public Post findOneById(Long id) {
        return em.find(Post.class, id);
    }

    public List<Post> findAllRandom() {
        return em.createQuery("select p from Post p order by rand()", Post.class)
                .getResultList();
    }

    public List<Post> findAllNew() {
        return em.createQuery("select p from Post p order by p.startDate desc", Post.class)
                .getResultList();
    }

    public List<Post> findAllEnd() {
        return em.createQuery("select p from Post p order by p.endDate", Post.class)
                .getResultList();
    }

//    public List<Post> findAllByTopic(Topic topic) {
//        return em.createQuery("select p from Post p where p.topic = :topic")
//                .setParameter("topic", topic)
//                .getResultList();
//    }
//
//    public List<Post> findAllByTarget(Target target) {
//        return em.createQuery("select p from Post p where p.target = :target")
//                .setParameter("target", target)
//                .getResultList();
//    }

    public void delete(Post post) {
        em.remove(post);
    }
}
