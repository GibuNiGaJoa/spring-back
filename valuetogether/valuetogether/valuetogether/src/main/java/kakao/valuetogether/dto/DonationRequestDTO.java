package kakao.valuetogether.dto;

import kakao.valuetogether.domain.Member;
import kakao.valuetogether.domain.Post;
import kakao.valuetogether.domain.enums.DonationType;
import lombok.Data;

import java.util.Date;

@Data
public class DonationRequestDTO {
    private Post post;
    private Member member;
    private DonationType donationType;
    private Integer donationAmount;
    private Date donationDate;
}
