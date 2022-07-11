package kakao.valuetogether.service;

import kakao.valuetogether.domain.*;
import kakao.valuetogether.repository.DonationRepository;
import kakao.valuetogether.repository.MemberRepository;
import kakao.valuetogether.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class DonationServiceTest {

    @Autowired MemberRepository memberRepository;
    @Autowired PostRepository postRepository;
    @Autowired DonationRepository donationRepository;
    @Autowired DonationService donationService;

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
    public void donateByDirect() {
        donationService.donateByDirect(Optional.of(findMember), null, 1000);

        Donation donationByMember = donationRepository.findByMember(findMember);
        Assertions.assertThat(donationByMember.getAmountDirect()).isEqualTo(1000);
    }

    @Test
    public void donateByCheer() {

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
