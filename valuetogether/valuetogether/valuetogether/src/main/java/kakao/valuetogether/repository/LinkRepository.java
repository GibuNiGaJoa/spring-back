package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Link;
import kakao.valuetogether.domain.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class LinkRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Link link) {
        em.persist(link);
        return link.getId();
    }
}
