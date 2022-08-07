package kakao.valuetogether.domain;

import kakao.valuetogether.domain.enums.Target;
import kakao.valuetogether.domain.enums.Topic;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String subTitle;

    @Column(nullable = false)
    private String content;

//    @Enumerated(EnumType.STRING)
//    private Topic topic;
//
//    @Enumerated(EnumType.STRING)
//    private Target target;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
//    private List<Link> links = new ArrayList<>();

    @Column(name = "target_amount", nullable = false)
    private Integer targetAmount;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "is_confirm", nullable = false)
    private Boolean isConfirm = false;

    public Post(Member member, String title, String subTitle, String content, Integer targetAmount, Date startDate, Date endDate, Boolean isConfirm) {
        this.member = member;
        this.title = title;
        this.subTitle = subTitle;
        this.content = content;
        this.targetAmount = targetAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isConfirm = isConfirm;
    }

    public Post() {
    }

    public void addLink(Link link) {
        link.setPost(this);
        //this.links.add(link);
    }

    public static Post createPost(Post post, Link... links) {
        for (Link link : links)
            post.addLink(link);
        return post;
    }
}
