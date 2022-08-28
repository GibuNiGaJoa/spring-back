package kakao.valuetogether.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MyPageCommentDTO<T> {
    private Integer countComment;
    private T commentVOs;
}
