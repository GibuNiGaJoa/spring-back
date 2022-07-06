package kakao.valuetogether.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class TargetPost {

    @Id @GeneratedValue
    private Long id;

    @Id
    @Column(name = "post_id")
    private Long postId;
}
