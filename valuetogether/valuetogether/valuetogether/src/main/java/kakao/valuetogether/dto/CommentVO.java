package kakao.valuetogether.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import kakao.valuetogether.domain.Member;
import kakao.valuetogether.domain.Post;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentVO {
    private Member member;
    private Post post;
    private String content;
    private Date date;
    private Integer likes;
    private Integer donationAmount;

    private String postTitle;
}
