package kakao.valuetogether.domain;

import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
public class Comment implements Serializable {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Member.class, optional = false)
    private Member member;

    @JoinColumn(name = "post_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Post.class, optional = false)
    private Post post;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    private Integer likes;

    public Comment(Member member, Post post, String content, Date date, Integer likes) {
        this.member = member;
        this.post = post;
        this.content = content;
        this.date = date;
        this.likes = likes;
    }

    public Comment() {}

    public void editContent(String content) {
        this.content = content;
    }

    public void addLikes() {
        this.likes += 1;
    }
}
