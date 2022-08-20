package kakao.valuetogether.service;

import kakao.valuetogether.api.CommentApiController;
import kakao.valuetogether.domain.*;
import kakao.valuetogether.domain.enums.DonationType;
import kakao.valuetogether.dto.DonationRequestDTO;
import kakao.valuetogether.dto.DonationResponseDTO;
import kakao.valuetogether.repository.DonationDetailRepository;
import kakao.valuetogether.repository.DonationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class DonationService {

    private final DonationRepository donationRepository;
    private final DonationDetailRepository donationDetailRepository;
    private final PostService postService;
    private final MemberService memberService;
    private final CommentService commentService;

    public void createDonation(DonationRequestDTO request) {
        Donation donation = new Donation(postService.findOneById(request.getPostId()));
        donationRepository.createDonation(donation);
    }

    public void createDonation(Post post) {
        Donation donation = new Donation(post);
        donationRepository.createDonation(donation);
    }

    public void donate(DonationRequestDTO request, Long memberId) {
        Member findMember = memberService.findOne(memberId);

        if(request.getDonationType() == DonationType.직접참여) {
            Post findPost = postService.findOneById(request.getPostId());
            Comment comment = new Comment(findMember, findPost);
            comment.setContent(request.getContent());
            comment.setDate(request.getDonationDate());
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

    public void donateComment(Long postId, Long memberId, Date donationDate) {
        Donation findDonation = donationRepository.findByPostId(postId);
        findDonation.donate(DonationType.댓글참여, 100);
        donationRepository.updateDonation(findDonation);

        DonationDetail donationDetail = DonationDetail.builder()
                .member(memberService.findOne(memberId))
                .post(postService.findOneById(postId))
                .donationType(DonationType.댓글참여)
                .donationAmount(100)
                .donationDate(donationDate).build();
        donationDetailRepository.createDonationDetail(donationDetail);
    }

    public Donation findDonationByPost(DonationRequestDTO request) {
        return donationRepository.findByPostId(request.getPostId());
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
                    .build());
        });

        return result;
    }

    public void removeDonation(DonationRequestDTO request) {
        Donation findDonation = donationRepository.findByPostId(request.getPostId());
        donationRepository.deleteDonation(findDonation);
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

}
