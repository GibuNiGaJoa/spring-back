package kakao.valuetogether.repository;

import kakao.valuetogether.domain.DonationDetail;
import kakao.valuetogether.domain.Member;
import kakao.valuetogether.domain.Post;
import kakao.valuetogether.domain.enums.DonationType;
import kakao.valuetogether.domain.enums.Target;
import kakao.valuetogether.domain.enums.Topic;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class DonationDetailRepositoryTest {

    @Autowired DonationDetailRepository donationDetailRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired PostRepository postRepository;

    Member savedMember;
    Post savedPost;
    DonationDetail savedDonationDetail;

    @BeforeEach
    public void beforeEach() {
        Member member = Member.builder().email("email").name("name").pw("pw").nickname("nickname").birthday("birthday").gender("man").address("address").phone("01077239811").build();
        Long memberId = memberRepository.save(member);
        this.savedMember = memberRepository.findById(memberId);

        Post post = new Post(savedMember, "title", "subtitle", "article", "static/image", Topic.건강한삶, Target.실버세대, 10000, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), false);
        Long postId = postRepository.save(post);
        this.savedPost = postRepository.findOneById(postId);

        DonationDetail donationDetail = new DonationDetail(savedMember, savedPost, Timestamp.valueOf(LocalDateTime.now()), 100, DonationType.응원참여);
        Long donationDetailId = donationDetailRepository.createDonationDetail(donationDetail);
        this.savedDonationDetail = donationDetailRepository.findById(donationDetailId);
    }

    @Test
    public void findDonationDetailByMember() {
        DonationDetail result = donationDetailRepository.findDonationDetailByMember(savedMember);
        Assertions.assertThat(this.savedDonationDetail).isEqualTo(result);
    }
}