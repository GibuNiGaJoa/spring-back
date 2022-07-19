package kakao.valuetogether.service;

import kakao.valuetogether.domain.Donation;
import kakao.valuetogether.domain.Member;
import kakao.valuetogether.domain.Post;
import kakao.valuetogether.repository.DonationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DonationService {

    private final DonationRepository donationRepository;

    public void donateByDirect(Optional<Member> member, Optional<Post> post, Integer amount) {
        Donation donation = null;

        if (member.isPresent())
            donation = donationRepository.findByMember(member.get());
        else if (post.isPresent())
            donation = donationRepository.findByPost(post.get());
        else
            throw new RuntimeException("member 혹은 post 객체 정보가 없습니다.");

        donateDirect(donation, amount);
    }

    public void donateByCheer(Optional<Member> member, Optional<Post> post) {
        Donation donation = null;

        if (member.isPresent())
            donation = donationRepository.findByMember(member.get());
        else if (post.isPresent())
            donation = donationRepository.findByPost(post.get());

        donateCheer(donation);
    }

    public void donateByShare(Optional<Member> member, Optional<Post> post) {
        Donation donation = null;

        if (member.isPresent())
            donation = donationRepository.findByMember(member.get());
        else if (post.isPresent())
            donation = donationRepository.findByPost(post.get());

        donateShare(donation);
    }

    // TO DO: 댓글 기부
    public void donateByComment(Optional<Member> member, Optional<Post> post) {
        Donation donation = null;

        if (member.isPresent())
            donation = donationRepository.findByMember(member.get());
        else if (post.isPresent())
            donation = donationRepository.findByPost(post.get());

        donateComment(donation);
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
