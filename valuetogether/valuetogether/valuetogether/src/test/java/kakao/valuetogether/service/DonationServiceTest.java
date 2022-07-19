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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    private Donation donation, findDonation;

    @BeforeEach
    void beforeEach() {
        findMember = getFindMember();
        findPost = getFindPost(findMember);
        donation = getDonation(findMember, findPost);
        findDonation = donationRepository.findByMember(findMember);
    }

    @Test
    public void donateByDirect() {
        donationService.donateByDirect(Optional.of(findMember), null, 1000);
        Donation donationByMember = donationRepository.findByMember(findMember);
        assertThat(donationByMember.getAmountDirect()).isEqualTo(1000);
        assertThat(donationByMember.getCountDirect()).isEqualTo(1);

        /**
         * null을 주었을 때 오류 발생
         * donationService.donateByDirect(null, Optional.of(findPost), 10000);
         */
        donationService.donateByDirect(Optional.empty(), Optional.of(findPost), 10000);
        Donation donationByPost = donationRepository.findByPost(findPost);
        assertThat(donationByPost.getAmountDirect()).isEqualTo(11000);
        assertThat(donationByPost.getCountDirect()).isEqualTo(2);
    }

    @Test
    public void donateByDirect_Exception() {
        assertThrows(RuntimeException.class, () -> {
            donationService.donateByDirect(Optional.empty(), Optional.empty(), 5000);
        });
    }

    @Test
    public void donateByCheer() {
        donationService.donateByCheer(Optional.empty(), Optional.of(findPost));
        Donation donationByPost = donationRepository.findByPost(findPost);
        assertThat(donationByPost.getAmountCheer()).isEqualTo(100);
        assertThat(donationByPost.getCountCheer()).isEqualTo(1);

        donationService.donateByCheer(Optional.of(findMember), Optional.empty());
        Donation donationByMember = donationRepository.findByMember(findMember);
        assertThat(donationByMember.getAmountCheer()).isEqualTo(200);
        assertThat(donationByMember.getCountCheer()).isEqualTo(2);
    }

    @Test
    public void donateByShare() {
        donationService.donateByShare(Optional.empty(), Optional.of(findPost));
        Donation donationByPost = donationRepository.findByPost(findPost);
        assertThat(donationByPost.getAmountShare()).isEqualTo(100);
        assertThat(donationByPost.getCountShare()).isEqualTo(1);

        donationService.donateByShare(Optional.of(findMember), Optional.empty());
        Donation donationByMember = donationRepository.findByMember(findMember);
        assertThat(donationByMember.getAmountShare()).isEqualTo(200);
        assertThat(donationByMember.getCountShare()).isEqualTo(2);
    }

    @Test
    public void donateByComment() {
        donationService.donateByComment(Optional.empty(), Optional.of(findPost));
        Donation donationByPost = donationRepository.findByPost(findPost);
        assertThat(donationByPost.getAmountComment()).isEqualTo(100);
        assertThat(donationByPost.getCountComment()).isEqualTo(1);

        donationService.donateByComment(Optional.of(findMember), Optional.empty());
        Donation donationByMember = donationRepository.findByMember(findMember);
        assertThat(donationByMember.getAmountComment()).isEqualTo(200);
        assertThat(donationByMember.getCountComment()).isEqualTo(2);
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