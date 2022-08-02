package kakao.valuetogether.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter @Setter
public class TagPost implements Serializable {

    @Id
    @JoinColumn(name = "tag_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tag tag;

    @JoinColumn(name = "post_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;
}
