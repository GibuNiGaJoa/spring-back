package kakao.valuetogether.service;

import kakao.valuetogether.domain.Donation;
import kakao.valuetogether.domain.Member;
import kakao.valuetogether.domain.Post;
import kakao.valuetogether.repository.DonationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DonationService {

    private final DonationRepository donationRepository;

    public DonationService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public void donateByDirect(Optional<Member> member, Optional<Post> post, Integer amount) {
        if(member.isPresent()) {
            member.ifPresent(m -> {
                Donation donationByMember = donationRepository.findByMember(m);
                donationByMember.addAmountDirect(amount);
                donationByMember.addCountDirect();
            });
        } else {
            post.ifPresent(p -> {
                Donation donationByPost = donationRepository.findByPost(p);
                donationByPost.addAmountDirect(amount);
                donationByPost.addCountDirect();
            });
        }
    }

    // TO DO: 응원 기부
//    public void donateByCheer()

    // TO DO: 공유 기부

    // TO DO: 댓글 기부

    // TO DO: 전체 기부금 조회

}
