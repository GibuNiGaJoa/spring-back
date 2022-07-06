package kakao.valuetogether.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Donation implements Serializable {

    @Id
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Id
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @Column(name = "direct_donation")
    private Integer directDonation;

    @Column(name = "cheer_donation")
    private Integer cheerDonation;

    @Column(name = "share_donation")
    private Integer shareDonation;

    @Column(name = "comment_donation")
    private Integer commentDonation;

}
