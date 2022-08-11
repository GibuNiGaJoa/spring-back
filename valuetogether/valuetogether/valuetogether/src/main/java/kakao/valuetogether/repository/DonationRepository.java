package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Donation;
import kakao.valuetogether.domain.Post;
import kakao.valuetogether.dto.DonationRequestDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 기부 초기 생성
 * 기부 수정 -> param: Post, donationType, donationAmount
 * 기부 조회 -> param: Post. return: one donation
 * 기부 삭제 -> param: Post
 */
@Repository
public class DonationRepository {

    @PersistenceContext
    private EntityManager em;

    public Long saveDonation(Donation donation) {
        em.persist(donation);
        return donation.getId();
    }

    public Donation findById(Long id) {
        return em.find(Donation.class, id);
    }

    public Long updateDonation(Donation donation) {
        em.persist(donation);
        return donation.getId();
    }

    public void deleteDonation(Donation donation) {
        em.remove(donation);
    }
}
