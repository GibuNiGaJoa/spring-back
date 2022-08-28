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
//@Builder
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentVO {
    private Long commentId;
//    private Member member;
    private Long postId;
    private String content;
    private Date date;
    private Integer likes;
//    private Integer donationAmount;
    private String postTitle;
    private boolean likeStatus;
}
