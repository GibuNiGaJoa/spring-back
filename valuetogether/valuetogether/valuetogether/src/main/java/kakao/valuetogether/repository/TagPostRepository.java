package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Post;
import kakao.valuetogether.domain.Tag;
import kakao.valuetogether.domain.TagPost;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class TagPostRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(TagPost tagPost) {
        em.persist(tagPost);
        return tagPost.getId();
    }
}
