package kakao.valuetogether.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kakao.valuetogether.domain.Member;
import kakao.valuetogether.domain.Post;
import kakao.valuetogether.domain.enums.DonationType;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DonationResponseDTO {
    private Long memberId, postId;

    private Integer amountDirect;
    private Integer amountCheer;
    private Integer amountShare;
    private Integer amountComment;
    private Integer countDirect;
    private Integer totalAmount;

    private Date donationDate;
    private Integer donationAmount;
    private DonationType donationType;

    private String postTitle;
}
