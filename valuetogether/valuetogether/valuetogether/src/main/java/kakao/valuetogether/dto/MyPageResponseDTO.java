package kakao.valuetogether.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyPageResponseDTO<T> {
    private String memberName;
    private String gender;

    private Integer totalAmount;
    private Integer countDonation;
    private Integer amountDirect;
    private Integer amountParticipation;

    private Integer countComment;
    private T comments;
}
