package kakao.valuetogether.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class TopicPost {

    @Id @GeneratedValue
    @Column(name = "target_id")
    private Long id;

    @Id
    @Column(name = "post_id")
    private Long postId;
}
