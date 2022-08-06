package kakao.valuetogether.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter @Setter
public class TagPost {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_post_id")
    private Long id;

    @JoinColumn(name = "tag_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Tag tag;

    @JoinColumn(name = "post_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    public TagPost(Tag tag, Post post) {
        this.tag = tag;
        this.post = post;
    }

    public TagPost() {
    }
}
