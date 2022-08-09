package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Post;
import kakao.valuetogether.domain.Tag;
import kakao.valuetogether.domain.TagPost;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class TagPostRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(TagPost tagPost) {
        em.persist(tagPost);
        return tagPost.getId();
    }

    public Optional<TagPost> findById(Long id) {
        List<TagPost> findTagPost = em.createQuery("select t from TagPost t where t.id = :id")
                .setParameter("id", id)
                .getResultList();
        return findTagPost.stream().findAny();
    }
}
