package kakao.valuetogether.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @Id
    @Column(name = "member_id")
    private Long memberId;

    @Id
    @Column(name = "post_id")
    private Long postId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String date;

    private Integer like;
}
