package kakao.valuetogether.service;

import kakao.valuetogether.domain.DonationDetail;
import kakao.valuetogether.domain.Member;
import kakao.valuetogether.dto.MyPageCommentDTO;
import kakao.valuetogether.dto.MyPageDonationDetailDTO;
import kakao.valuetogether.dto.MyPageResponseDTO;
import kakao.valuetogether.repository.DonationDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MemberService memberService;
    private final DonationService donationService;
    private final CommentService commentService;

    public MyPageResponseDTO accessMyPage(Long memberId) {
        Member findMember = memberService.findOne(memberId);
        MyPageDonationDetailDTO donationDetail = donationService.getMyPageDonationDetailDTO(findMember);
        MyPageCommentDTO comment = commentService.getMyPageCommentDTO(findMember);

        MyPageResponseDTO result = MyPageResponseDTO.builder()
                .memberName(findMember.getName())
                .gender(findMember.getGender())

                .totalAmount(donationDetail.getTotalAmount())
                .countDonation(donationDetail.getCountDonation())
                .amountDirect(donationDetail.getAmountDirect())
                .amountParticipation(donationDetail.getAmountParticipation())

                .countComment(comment.getCountComment())
                .comments(comment.getCommentVOs())

                .build();
        return result;
    }
}
