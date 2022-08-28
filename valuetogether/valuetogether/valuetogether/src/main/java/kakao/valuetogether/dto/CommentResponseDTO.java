package kakao.valuetogether.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class CommentResponseDTO {

    private Long id;
    private String nickname;
    private String content;
    private Date date;
    private Integer likes;
    private Boolean likeStatus;
    private Boolean status;
    private Integer donationAmount;
    private String gender;
}
