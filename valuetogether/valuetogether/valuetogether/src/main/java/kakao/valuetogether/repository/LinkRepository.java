package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Link;
import kakao.valuetogether.domain.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class LinkRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Link link) {
        em.persist(link);
        return link.getId();
    }

    public List<Link> findLinkByPost(Post post) {
        List<Link> linkList = em.createQuery("select l from Link l where l.post = :post", Link.class)
                .setParameter("post", post)
                .getResultList();
        return linkList;
    }
}
