package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Donation;
import kakao.valuetogether.domain.Member;
import kakao.valuetogether.domain.Post;
import kakao.valuetogether.domain.enums.Target;
import kakao.valuetogether.domain.enums.Topic;
import kakao.valuetogether.dto.DonationRequestDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
//@Rollback(value = false)
public class DonationRepositoryTest {

    @Autowired private DonationRepository donationRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private PostRepository postRepository;

    Member savedMember;
    Post savedPost;
    Donation savedDonation;

    @BeforeEach
    public void beforeEach() {
        Member member = Member.builder().email("email").name("name").pw("pw").nickname("nickname").birthday("birthday").gender("man").address("address").phone("01077239811").build();
        Long memberId = memberRepository.save(member);
        this.savedMember = memberRepository.findById(memberId);

        Post post = new Post(savedMember, "title", "subtitle", "article", "static/image", Topic.건강한삶, Target.실버세대, 10000, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), false);
        Long postId = postRepository.save(post);
        savedPost = postRepository.findOneById(postId);

        Donation donation = new Donation(savedPost);
        Long donationId = donationRepository.saveDonation(donation);
        savedDonation = donationRepository.findById(donationId);
    }

    @Test
    public void saveDonation() {
        Donation donation = new Donation(savedPost);
        Long result = donationRepository.saveDonation(donation);
        System.out.println("result = " + result);

        Post post2 = new Post(savedMember, "title", "subtitle", "article", "static/image", Topic.건강한삶, Target.실버세대, 10000, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), false);
        Long newId = postRepository.save(post2);
        Post otherPost = postRepository.findOneById(newId);

        Donation donation2 = new Donation(otherPost);
        donationRepository.saveDonation(donation2);
    }

    @Test
    public void updateDonation() {
        Donation findDonation = donationRepository.findById(savedPost.getId());
        assertThat(findDonation.getAmountCheer()).isEqualTo(0);

        findDonation.addAmountCheer();
        donationRepository.updateDonation(findDonation);
        assertThat(findDonation.getAmountCheer()).isEqualTo(100);
    }

    @Test
    public void deleteDonation() {
        donationRepository.deleteDonation(savedDonation);
    }
}
