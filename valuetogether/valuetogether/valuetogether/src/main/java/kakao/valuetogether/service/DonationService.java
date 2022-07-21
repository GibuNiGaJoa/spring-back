package kakao.valuetogether.service;

import kakao.valuetogether.domain.Donation;
import kakao.valuetogether.domain.Member;
import kakao.valuetogether.domain.Post;
import kakao.valuetogether.repository.DonationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DonationService {

    private final DonationRepository donationRepository;

    public void donateByDirect(Optional<Member> member, Optional<Post> post, Integer amount) {
        Donation donation = checkExistence(member, post);
        donateDirect(donation, amount);
    }

    public void donateByCheer(Optional<Member> member, Optional<Post> post) {
        Donation donation = checkExistence(member, post);
        donateCheer(donation);
    }

    public void donateByShare(Optional<Member> member, Optional<Post> post) {
        Donation donation = checkExistence(member, post);
        donateShare(donation);
    }

    public void donateByComment(Optional<Member> member, Optional<Post> post) {
        Donation donation = checkExistence(member, post);
        donateComment(donation);
    }

    public Donation checkExistence(Optional<Member> member, Optional<Post> post) {
        Donation donation = null;

        if (member.isPresent())
            donation = donationRepository.findByMember(member.get());
        else if (post.isPresent())
            donation = donationRepository.findByPost(post.get());
        else
            throw new RuntimeException("member 혹은 post 객체 정보가 없습니다.");

        return donation;
    }

    public Integer getTotalDonation(Donation donation) {
        return donation.getTotalAmount();
    }

    public void donateDirect(Donation donation, Integer amount) {
        donationRepository.addAmountDirect(donation, amount);
        donationRepository.addCountDirect(donation);
    }

    public void donateCheer(Donation donation) {
        donationRepository.addAmountCheer(donation);
        donationRepository.addCountCheer(donation);
    }

    public void donateShare(Donation donation) {
        donationRepository.addAmountShare(donation);
        donationRepository.addCountShare(donation);
    }

    public void donateComment(Donation donation) {
        donationRepository.addAmountComment(donation);
        donationRepository.addCountComment(donation);
    }
}
