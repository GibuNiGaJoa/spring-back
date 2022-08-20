package kakao.valuetogether.repository;

import kakao.valuetogether.domain.DonationDetail;
import kakao.valuetogether.domain.Member;
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

    public Long updateDonationDetail(DonationDetail donationDetail) {
        em.persist(donationDetail);
        return donationDetail.getId();
    }

    public void deleteDonationDetail(DonationDetail donationDetail) {
        em.remove(donationDetail);
    }
}
