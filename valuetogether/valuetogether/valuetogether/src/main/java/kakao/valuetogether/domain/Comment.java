package kakao.valuetogether.domain;

import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
public class Comment implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @JoinColumn(name = "member_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "post_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)

    private Post post;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)

    @Temporal(TemporalType.TIMESTAMP)
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

    public boolean minusLikes() {
        if(this.likes <= 0)
            return false;

        this.likes -= 1;
        return true;
    }
}
