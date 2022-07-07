package kakao.valuetogether.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter @Setter
public class TopicPost implements Serializable {

    @Id
    @JoinColumn(name = "topic_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Topic topic;

    @JoinColumn(name = "post_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;
}
