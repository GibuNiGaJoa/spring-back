package kakao.valuetogether.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Topic {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private Long id;

    @Column(name = "topic_name", nullable = false)
    private String topicName;

    public Topic(String topicName) {
        this.topicName = topicName;
    }

    public Topic() {

    }
}
