package kakao.valuetogether.domain;

import kakao.valuetogether.domain.enums.DonationType;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
public class DonationDetail {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    Member member;

    @JoinColumn(name = "post_id")
    @OneToOne(fetch = FetchType.LAZY)
    Post post;

    @Column(name = "donation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date donationDate;

    @Column(name = "donation_amount", nullable = false)
    private Integer donationAmount;

    @Column(name = "donation_type", nullable = false)
    private DonationType donationType;

}
