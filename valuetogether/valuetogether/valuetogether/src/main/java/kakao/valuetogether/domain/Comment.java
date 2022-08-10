package kakao.valuetogether.domain;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@ToString
public class Comment implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @JoinColumn(name = "member_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "post_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date commentSaveDate;

    private Integer likes = 0;

    public Comment(Member member, Post post, String content, Date commentSaveDate, Integer likes) {
        this.member = member;
        this.post = post;
        this.content = content;
        this.commentSaveDate = commentSaveDate;
        this.likes = likes;
    }

    public Comment() {}

    public void editContent(String content) {
        this.content = content;
    }

    public void addLikes() {
        this.likes += 1;
    }

    public void minusLikes() {
        this.likes -= 1;
    }
}
