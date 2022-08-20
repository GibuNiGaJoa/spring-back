package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Donation;
import kakao.valuetogether.domain.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class DonationRepository {

    @PersistenceContext
    private EntityManager em;

    public void createDonation(Donation donation) {
        em.persist(donation);
    }

    public Donation findByPostId(Long id) {
        return em.find(Donation.class, id);
    }

    public Donation updateDonation(Donation donation) {
        em.persist(donation);
        return donation;
    }

    public void deleteDonation(Donation donation) {
        em.remove(donation);
    }
}
