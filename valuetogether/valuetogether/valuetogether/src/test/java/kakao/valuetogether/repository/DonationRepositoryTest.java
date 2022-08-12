package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Donation;
import kakao.valuetogether.domain.Member;
import kakao.valuetogether.domain.Post;
import kakao.valuetogether.domain.enums.DonationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class DonationRepositoryTest {

    @Autowired private DonationRepository donationRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private PostRepository postRepository;

    Member savedMember;
    Post savedPost;
    Donation savedDonation;

    @BeforeEach
    public void setUp() {
        Member member = Member.builder().email("email").name("name").pw("pw").nickname("nickname").birthday("birthday").gender("man").address("address").phone("01077239811").build();
        Long memberId = memberRepository.save(member);
        this.savedMember = memberRepository.findById(memberId);

        Post post = new Post(savedMember, "title", "proposer", "content", 1000, new Date(1000), new Date(2000), "image", false);
        Long postId = postRepository.save(post);
        savedPost = postRepository.findOneById(postId);

        Donation donation = new Donation(savedPost);
        donationRepository.createDonation(donation);
        savedDonation = donationRepository.findByPostId(savedPost.getId());
    }

    @Test
    public void updateDonation() {
        Donation findDonation = donationRepository.findByPostId(savedPost.getId());
        assertThat(findDonation.getAmountCheer()).isEqualTo(0);

        findDonation.donate(DonationType.응원참여, 100);
        donationRepository.updateDonation(findDonation);
        assertThat(findDonation.getAmountCheer()).isEqualTo(100);
    }

    @Test
    public void deleteDonation() {
        donationRepository.deleteDonation(savedDonation);
    }
}
