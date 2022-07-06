package kakao.valuetogether.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Donation {

    @Id
    private Long memberId;

    @Id
    private Long postId;

    @Column(name = "direct_donation")
    private Integer directDonation;

    @Column(name = "cheer_donation")
    private Integer cheerDonation;

    @Column(name = "share_donation")
    private Integer shareDonation;

    @Column(name = "comment_donation")
    private Integer commentDonation;

}
