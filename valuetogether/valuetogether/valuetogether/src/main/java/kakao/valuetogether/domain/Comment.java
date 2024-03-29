package kakao.valuetogether.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
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
    @Temporal(TemporalType.DATE)
    private Date date;

    private Integer likes = 0;

    @Column(name = "donation_amount")
    private Integer donationAmount;

    public Comment(Member member, Post post, String content, Date date, Integer likes) {
        this.member = member;
        this.post = post;
        this.content = content;
        this.date = date;
        this.likes = likes;
    }

    public Comment(Member member, Post post) {
        this.member = member;
        this.post = post;
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
