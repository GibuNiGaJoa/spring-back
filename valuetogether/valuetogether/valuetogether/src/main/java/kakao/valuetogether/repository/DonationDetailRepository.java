package kakao.valuetogether.repository;

import kakao.valuetogether.domain.DonationDetail;
import kakao.valuetogether.domain.Member;
import kakao.valuetogether.domain.Post;
import kakao.valuetogether.domain.enums.DonationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DonationDetailRepository {

    @PersistenceContext
    private EntityManager em;

    public Long createDonationDetail(DonationDetail donationDetail) {
        em.persist(donationDetail);
        return donationDetail.getId();
    }

    public DonationDetail findById(Long id) {
        return em.find(DonationDetail.class, id);
    }

    public List<DonationDetail> findDonationDetailsByMember(Member member) {
        return em.createQuery("select d from DonationDetail d where d.member = :member")
                .setParameter("member", member)
                .getResultList();
    }

    public boolean existsByMemberAndPost(Member member, Post post, DonationType donationType) {
        List<DonationDetail> result = em.createQuery("select d from DonationDetail d where d.member = :member and d.post = :post and d.donationType = :donationType")
                .setParameter("member", member)
                .setParameter("post", post)
                .setParameter("donationType", donationType)
                .getResultList();

        if(result.size() != 0)
            return true;
        return false;
    }

    public Long updateDonationDetail(DonationDetail donationDetail) {
        em.persist(donationDetail);
        return donationDetail.getId();
    }

    public void deleteDonationDetail(DonationDetail donationDetail) {
        em.remove(donationDetail);
    }
}
