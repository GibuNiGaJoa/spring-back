package kakao.valuetogether.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kakao.valuetogether.domain.enums.DonationType;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class DonationDetail {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donation_detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", unique = true)
    private Post post;

    @Column(name = "donation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date donationDate;

    @Column(name = "donation_amount", nullable = false)
    private Integer donationAmount;

    @Column(name = "donation_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DonationType donationType;

    public DonationDetail() {}

    public DonationDetail(Member member, Post post, Date donationDate, Integer donationAmount, DonationType donationType) {
        this.member = member;
        this.post = post;
        this.donationDate = donationDate;
        this.donationAmount = donationAmount;
        this.donationType = donationType;
    }
}
