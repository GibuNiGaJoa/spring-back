package kakao.valuetogether.repository;

import kakao.valuetogether.domain.LikeDetail;
import kakao.valuetogether.domain.Member;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class LikeDetailRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(LikeDetail likeDetail) {
        em.persist(likeDetail);
        return likeDetail.getId();
    }
}
