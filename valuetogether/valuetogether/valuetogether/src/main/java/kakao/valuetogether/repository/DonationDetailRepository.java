package kakao.valuetogether.repository;

import kakao.valuetogether.domain.DonationDetail;
import kakao.valuetogether.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * TODO
 * 기부내역 생성
 * 기부내역 수정
 * 기부내역 조회
 * 기부내역 삭제
 */
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

    public DonationDetail findDonationDetailByMember(Member member) {
        return (DonationDetail) em.createQuery("select d from DonationDetail d where d.member = :member")
                .setParameter("member", member)
                .getSingleResult();
    }

    public Long updateDonationDetail(DonationDetail donationDetail) {
        em.persist(donationDetail);
        return donationDetail.getId();
    }

    public void deleteDonationDetail(DonationDetail donationDetail) {
        em.remove(donationDetail);
    }
}
