package kakao.valuetogether.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Target {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "target_name", nullable = false)
    private String targetName;

    public Target(String targetName) {
        this.targetName = targetName;
    }

    public Target() {

    }
}
