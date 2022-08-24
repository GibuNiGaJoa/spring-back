package kakao.valuetogether.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MyPageDonationDetailDTO {
    private Integer totalAmount;
    private Integer countDonation;
    private Integer amountDirect;
    private Integer amountParticipation;
}
