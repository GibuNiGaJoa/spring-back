package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Comment;
import kakao.valuetogether.domain.LikeDetail;
import kakao.valuetogether.domain.Member;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class LikeDetailRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(LikeDetail likeDetail) {
        em.persist(likeDetail);
        return likeDetail.getId();
    }

    public void delete(LikeDetail likeDetail) {
        em.remove(likeDetail);
    }

    public Optional<LikeDetail> findOne(Comment comment, Member member) {
        List<LikeDetail> findLikeDetail = em.createQuery("select l from LikeDetail l where l.comment =:comment and l.member =:member", LikeDetail.class)
                .setParameter("comment", comment)
                .setParameter("member", member)
                .getResultList();
        return findLikeDetail.stream().findAny();
    }

    public Optional<LikeDetail> findStatus(Comment comment, Member member) {
        List<LikeDetail> findLikeDetail = em.createQuery("select l from LikeDetail l where l.comment =:comment and l.member =:member", LikeDetail.class)
                .setParameter("comment", comment)
                .setParameter("member", member)
                .getResultList();
        return findLikeDetail.stream().findAny();
    }




}
