package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Donation;
import kakao.valuetogether.domain.Member;
import kakao.valuetogether.domain.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class DonationRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Donation donation) {
        em.persist(donation);
    }

    public Donation findByMember(Member member) {
        return em.createQuery("select d from Donation d where d.member = :member", Donation.class)
                .setParameter("member", member)
                .getSingleResult();
    }

    public Donation findByPost(Post post) {
        return em.createQuery("select d from Donation d where d.post = :post", Donation.class)
                .setParameter("post", post)
                .getSingleResult();
    }

    public void addAmountDirect(Donation donation, Integer amount) {
        donation.addAmountDirect(amount);
    }

    public void addAmountCheer(Donation donation, Integer amount) {
        donation.addAmountCheer(amount);
    }

    public void addAmountShare(Donation donation, Integer amount) {
        donation.addAmountShare(amount);
    }

    public void addAmountComment(Donation donation, Integer amount) {
        donation.addAmountComment(amount);
    }

    public void addCountDirect(Donation donation) {
        donation.addCountDirect();
    }

    public void addCountCheer(Donation donation) {
        donation.addCountCheer();
    }

    public void addCountShare(Donation donation) {
        donation.addCountShare();
    }

    public void addCountComment(Donation donation) {
        donation.addCountComment();
    }

}
