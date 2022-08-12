package kakao.valuetogether.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Link {

    @Id @GeneratedValue
    @Column(name = "link_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String link;

    public Link(Post post, String link) {
        this.post = post;
        this.link = link;
    }

    public Link() {

    }

}
