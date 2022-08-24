package kakao.valuetogether.dto;

import kakao.valuetogether.domain.Member;
import kakao.valuetogether.domain.Post;
import kakao.valuetogether.domain.enums.DonationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
public class DonationRequestDTO {
    private Long postId;
    private DonationType donationType;
    private Integer donationAmount;
    private Date donationDate;

    private String commentContent;
}
