package kakao.valuetogether.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter @Setter
public class TargetPost implements Serializable {

    @Id
    @JoinColumn(name = "target_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Target target;

    @JoinColumn(name = "post_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;
}
