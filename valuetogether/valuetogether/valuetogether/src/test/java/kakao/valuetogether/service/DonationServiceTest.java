package kakao.valuetogether.service;

import kakao.valuetogether.domain.Donation;
import kakao.valuetogether.domain.DonationDetail;
import kakao.valuetogether.domain.Member;
import kakao.valuetogether.domain.Post;
import kakao.valuetogether.domain.enums.DonationType;
import kakao.valuetogether.dto.DonationRequestDTO;
import kakao.valuetogether.dto.DonationResponseDTO;
import kakao.valuetogether.repository.MemberRepository;
import kakao.valuetogether.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class DonationServiceTest {

    @Autowired private DonationService donationService;
    @Autowired private MemberRepository memberRepository;
    @Autowired private PostRepository postRepository;

    Member savedMember;
    Post savedPost;
    DonationRequestDTO request;

    DonationType donationType = DonationType.직접참여;
    Integer donationAmount = 6666;
    Date donationDate = Date.valueOf(LocalDate.now());

    @BeforeEach
    public void setUp() {
        Member member = Member.builder().email("email").name("name").pw("pw").nickname("nickname").birthday("birthday").gender("man").address("address").phone("01077239811").build();
        Long memberId = memberRepository.save(member);
        this.savedMember = memberRepository.findById(memberId);

        Post post = new Post(savedMember, "title", "proposer", "content", 1000, new Date(1111), new Date(2222), "image", false);
        Long postId = postRepository.save(post);
        this.savedPost = postRepository.findOneById(postId);

        request = new DonationRequestDTO(this.savedPost.getId(), this.savedMember.getId(), donationType, donationAmount, donationDate);
        donationService.createDonation(request);
    }

    @Test
    public void createDonation_Post() {
        Post post = new Post(savedMember, "title", "proposer", "content", 1000, new Date(1111), new Date(2222), "image", false);
        donationService.createDonation(post);
    }

    @Test
    public void donate() {
        donationService.donate(request);
    }

    @Test
    public void findDonationByPost() {
        donationService.donate(request);

        Donation findDonation = donationService.findDonationByPost(request);
        assertThat(findDonation.getAmountDirect()).isEqualTo(donationAmount);
    }

    @Test
    public void findDonationDetailsByMember() {
        donationService.donate(request);

        List<DonationResponseDTO> result = donationService.findDonationDetailByMember(request);
        result.forEach(donationDetail -> System.out.println("donationDetail = " + donationDetail));
    }

    @Test
    public void removeDonation() {
        donationService.removeDonation(request);
        Donation result = donationService.findDonationByPost(request);
        Assertions.assertThat(result).isEqualTo(null);
    }
}
