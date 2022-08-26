package kakao.valuetogether.service;

import kakao.valuetogether.domain.*;
import kakao.valuetogether.domain.enums.DonationType;
import kakao.valuetogether.dto.DonationRequestDTO;
import kakao.valuetogether.dto.DonationResponseDTO;
import kakao.valuetogether.dto.MyPageDonationDetailDTO;
import kakao.valuetogether.repository.DonationDetailRepository;
import kakao.valuetogether.repository.DonationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
@Transactional
public class DonationService {

    private final DonationRepository donationRepository;
    private final DonationDetailRepository donationDetailRepository;
    private final PostService postService;
    private final MemberService memberService;
    private final CommentService commentService;

    public void createDonation(Post post) {
        Donation donation = new Donation(post);
        donationRepository.createDonation(donation);
    }

    public void donate(DonationRequestDTO request, Long memberId) {
        Member findMember = memberService.findOne(memberId);

        if(request.getDonationType() == DonationType.직접참여) {
            Post findPost = postService.findOneById(request.getPostId());

            Comment comment = new Comment(findMember, findPost);
            comment.setContent(request.getCommentContent());
            comment.setDate(request.getDonationDate());
            comment.setLikes(0);
            comment.setDonationAmount(request.getDonationAmount());
            commentService.enroll(comment);
        }

        Donation findDonation = donationRepository.findByPostId(request.getPostId());
        findDonation.donate(request.getDonationType(), request.getDonationAmount());
        donationRepository.updateDonation(findDonation);

        DonationDetail donationDetail = DonationDetail.builder()
                .member(findMember)
                .post(postService.findOneById(request.getPostId()))
                .donationType(request.getDonationType())
                .donationAmount(defineDonationAmount(request.getDonationType(), request.getDonationAmount()))
                .donationDate(request.getDonationDate()).build();
        donationDetailRepository.createDonationDetail(donationDetail);
    }
    private Integer defineDonationAmount(DonationType donationType, Integer donationAmount) {
        if(donationType == DonationType.직접참여)
            return donationAmount;
        else
            return 100;
    }

    public void donateComment(Member member, Long postId, Date donationDate) {
        Donation findDonation = donationRepository.findByPostId(postId);
        findDonation.donate(DonationType.댓글참여, 100);
        donationRepository.updateDonation(findDonation);

        DonationDetail donationDetail = DonationDetail.builder()
                .member(member)
                .post(postService.findOneById(postId))
                .donationType(DonationType.댓글참여)
                .donationAmount(100)
                .donationDate(donationDate).build();
        donationDetailRepository.createDonationDetail(donationDetail);
    }

    public Donation findDonationByPost(Post post) {
        return donationRepository.findByPostId(post.getId());
    }

    public List<DonationResponseDTO> findDonationDetailByMember(Long memberId) {
        Member member = memberService.findOne(memberId);
        List<DonationDetail> findResult = (ArrayList)donationDetailRepository.findDonationDetailsByMember(member);

        List<DonationResponseDTO> result = new ArrayList<>();

        findResult.forEach(donationDetail -> {
            result.add(DonationResponseDTO.builder()
                    .memberId(donationDetail.getMember().getId())
                    .postId(donationDetail.getPost().getId())
                    .donationDate(donationDetail.getDonationDate())
                    .donationAmount(donationDetail.getDonationAmount())
                    .donationType(donationDetail.getDonationType())
                    .postTitle(donationDetail.getPost().getTitle())
                    .build());
        });

        return result;
    }

    public DonationResponseDTO createDonationResponse(Donation donation) {
        return DonationResponseDTO.builder()
                .amountDirect(donation.getAmountDirect())
                .amountCheer(donation.getAmountCheer())
                .amountShare(donation.getAmountShare())
                .amountComment(donation.getAmountComment())
                .countDirect(donation.getCountDirect())
                .totalAmount(donation.getTotalAmount())
                .build();
    }

    public DonationResponseDTO createDonationDetailResponse(DonationRequestDTO request) {
        return DonationResponseDTO.builder()
                .donationDate(request.getDonationDate())
                .donationAmount(request.getDonationAmount())
                .donationType(request.getDonationType())
                .build();
    }

    public MyPageDonationDetailDTO getMyPageDonationDetailDTO(Member member) {
        AtomicInteger totalAmount = new AtomicInteger(0);
        AtomicInteger countDonation = new AtomicInteger(0);
        AtomicInteger amountDirect = new AtomicInteger(0);
        AtomicInteger amountParticipation = new AtomicInteger(0);

        List<DonationDetail> donationDetails = donationDetailRepository.findDonationDetailsByMember(member);
        donationDetails.forEach(donationDetail -> {
            totalAmount.addAndGet(donationDetail.getDonationAmount());
            countDonation.incrementAndGet();

            if(donationDetail.getDonationType() == DonationType.직접참여)
                amountDirect.addAndGet(donationDetail.getDonationAmount());
            else
                amountParticipation.addAndGet(donationDetail.getDonationAmount());
        });

        MyPageDonationDetailDTO result = MyPageDonationDetailDTO.builder()
                .totalAmount(totalAmount.intValue())
                .countDonation(countDonation.intValue())
                .amountDirect(amountDirect.intValue())
                .amountParticipation(amountParticipation.intValue())
                .build();
        return result;
    }

    public boolean isDonateCheer(Long memberId, Long postId) {
        Member member = memberService.findOne(memberId);
        Post post = postService.findOneById(postId);

        if(donationDetailRepository.existsByMemberAndPost(member, post, DonationType.응원참여))
            return true;
        return false;
    }

    public void removeDonation(DonationRequestDTO request) {
        Donation findDonation = donationRepository.findByPostId(request.getPostId());
        donationRepository.deleteDonation(findDonation);
    }
}
