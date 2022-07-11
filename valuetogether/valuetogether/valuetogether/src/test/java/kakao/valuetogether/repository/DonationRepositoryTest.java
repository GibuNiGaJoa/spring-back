package kakao.valuetogether.repository;

import kakao.valuetogether.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
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
        Integer directDonation = donation.getAmountDirect();
        assertThat(directDonation).isEqualTo(1000);

        donationRepository.addCountDirect(donation);
        assertThat(donation.getCountDirect()).isEqualTo(1);
    }

    @Test
    public void donateCheer() {
        donationRepository.addAmountCheer(donation, 10000);
        Integer cheerDonation = donation.getAmountCheer();
        assertThat(10000).isEqualTo(cheerDonation);

        donationRepository.addCountCheer(donation);
        assertThat(donation.getCountCheer()).isEqualTo(1);
    }

    @Test
    public void donateShare() {
        donationRepository.addAmountShare(donation, 10000);
        Integer shareDonation = donation.getAmountShare();
        assertThat(10000).isEqualTo(shareDonation);

        donationRepository.addCountShare(donation);
        assertThat(donation.getCountShare()).isEqualTo(1);
    }

    @Test
    public void donateComment() {
        donationRepository.addAmountComment(donation, 10000);
        Integer commentDonation = donation.getAmountComment();
        assertThat(10000).isEqualTo(commentDonation);

        donationRepository.addCountComment(donation);
        assertThat(donation.getCountComment()).isEqualTo(1);
    }

    public Member getFindMember() {
        Member member = new Member("email", "pw", "name", "111", "asdfasd", "asdf", "asfsa", "asdf");
        Long memberSavedId = memberRepository.save(member);
        return memberRepository.findById(memberSavedId);
    }

    public Post getFindPost(Member findMember) {
        Post post = new Post(findMember, "title", "subTitle", "article", "image", Topic.건강한삶, Target.실버세대, 100000, new Date(22, 7, 11), new Date(22, 8, 31), false);
        Long postSavedId = postRepository.save(post);
        return postRepository.findById(postSavedId);
    }

    public Donation getDonation(Member findMember, Post findPost) {
        Donation donation = new Donation(findMember, findPost);
        donationRepository.save(donation);
        return donationRepository.findByMember(findMember);
    }
}
