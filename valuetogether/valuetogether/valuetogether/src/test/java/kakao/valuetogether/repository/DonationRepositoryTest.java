package kakao.valuetogether.repository;

import kakao.valuetogether.domain.*;
import kakao.valuetogether.domain.enums.Target;
import kakao.valuetogether.domain.enums.Topic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class DonationRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired PostRepository postRepository;
    @Autowired DonationRepository donationRepository;

    private Member findMember;
    private Post findPost;
    private Donation donation;

    @BeforeEach
    void beforeEach() {
        findMember = getFindMember();
        findPost = getFindPost(findMember);
        donation = getDonation(findMember, findPost);
    }

    @Test
    public void saveAndFindByMember() {
        donationRepository.save(donation);
        Donation findDonation = donationRepository.findByMember(findMember);

        assertThat(donation).isEqualTo(findDonation);
    }

    @Test
    public void saveAndFindByPost() {
        donationRepository.save(donation);
        Donation findDonation = donationRepository.findByPost(findPost);

        assertThat(findDonation).isEqualTo(donation);
    }

    @Test
    public void donateDirect() {
        donationRepository.addAmountDirect(donation, 1000);

        Donation findDonation = donationRepository.findByMember(findMember);

        Integer directDonation = findDonation.getAmountDirect();
        assertThat(directDonation).isEqualTo(1000);

        donationRepository.addCountDirect(donation);
        assertThat(findDonation.getCountDirect()).isEqualTo(1);
    }

    @Test
    public void donateCheer() {
        donationRepository.addAmountCheer(donation);
        assertThat(100).isEqualTo(donation.getAmountCheer());

        donationRepository.addCountCheer(donation);
        assertThat(donation.getCountCheer()).isEqualTo(1);
    }

    @Test
    public void donateShare() {
        donationRepository.addAmountShare(donation);
        assertThat(100).isEqualTo(donation.getAmountShare());

        donationRepository.addCountShare(donation);
        assertThat(donation.getCountShare()).isEqualTo(1);
    }

    @Test
    public void donateComment() {
        donationRepository.addAmountComment(donation);
        assertThat(100).isEqualTo(donation.getAmountComment());

        donationRepository.addCountComment(donation);
        assertThat(donation.getCountComment()).isEqualTo(1);
    }

    public Member getFindMember() {
        Member member = new Member("email", "pw", "name", "111", "asdfasd", "asdf", "asfsa", "asdf");
        Long memberSavedId = memberRepository.save(member);
        return memberRepository.findById(memberSavedId);
    }

    public Post getFindPost(Member findMember) {
        Post post = new Post(findMember, "title", "subTitle", "article", "static/image", Topic.건강한삶, Target.실버세대, 100000, new Date(22, 7, 11), new Date(22, 8, 31), false);
        Long postSavedId = postRepository.save(post);
        return postRepository.findOneById(postSavedId);
    }

    public Donation getDonation(Member findMember, Post findPost) {
        Donation donation = new Donation(findMember, findPost);
        donationRepository.save(donation);
        return donationRepository.findByMember(findMember);
    }
}
