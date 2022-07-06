package kakao.valuetogether.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Target {

    @Id @GeneratedValue
    private Long id;

    @Column(name = "target_name", nullable = false)
    private String targetName;
}
