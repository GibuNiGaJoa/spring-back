package kakao.valuetogether.domain;

import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
public class Donation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @MapsId
    private Post post;

    @Column(name = "direct_donation")
    private Integer amountDirect = 0;

    @Column(name = "cheer_donation")
    private Integer amountCheer = 0;

    @Column(name = "share_donation")
    private Integer amountShare = 0;

    @Column(name = "comment_donation")
    private Integer amountComment = 0;

    @Column(name = "count_direct_donation")
    private Integer countDirect = 0;

    public Donation(Post post) {
        this.post = post;
    }

    public Donation() {}

    public void addAmountDirect(Integer amount) {
        this.amountDirect += amount;
    }

    public void addAmountCheer() {
        this.amountCheer += 100;
    }

    public void addAmountShare() {
        this.amountShare += 100;
    }

    public void addAmountComment() {
        this.amountComment += 100;
    }

    public void addCountDirect() {
        this.countDirect += 1;
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

}
