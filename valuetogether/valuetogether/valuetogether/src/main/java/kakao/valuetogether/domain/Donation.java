package kakao.valuetogether.domain;

import kakao.valuetogether.domain.enums.DonationType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Donation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @MapsId
    private Post post;

    @Column(name = "amount_direct")
    private Integer amountDirect;
    @Column(name = "count_direct")
    private Integer countDirect;
    @Column(name = "amount_cheer")
    private Integer amountCheer;
    @Column(name = "amount_share")
    private Integer amountShare;
    @Column(name = "amount_comment")
    private Integer amountComment;
    @Column(name = "amount_total")
    private Integer totalAmount;

    public Donation() {}

    public Donation(Post post) {
        this.post = post;
        this.amountDirect = 0;
        this.amountCheer = 0;
        this.amountShare = 0;
        this.amountComment = 0;
        this.countDirect = 0;
        this.totalAmount = 0;
    }

    public void donate(DonationType type, Integer amount) {
        switch (type) {
            case 직접참여:
                this.amountDirect += amount;
                this.countDirect += 1;
                break;
            case 응원참여:
                this.amountCheer += 100;
                break;
            case 공유참여:
                this.amountShare += 100;
                break;
            case 댓글참여:
                this.amountComment += 100;
                break;
        }

        this.totalAmount = getTotalAmount();
    }

    public Integer getTotalAmount() {
        return this.getAmountDirect() +
                this.getAmountCheer() +
                this.getAmountComment() +
                this.getAmountShare();
    }

    public Integer getCountCheer() {
        if(this.amountCheer != 0)
            return this.amountCheer / 100;
        return 0;
    }
    public Integer getCountShare() {
        if(this.amountShare != 0)
            return this.amountShare / 100;
        return 0;
    }
    public Integer getCountComment() {
        if(this.amountComment != 0)
            return this.amountComment / 100;
        return 0;
    }

    public void minusAmountComment() {
        this.amountComment -= 1;
    }
}
