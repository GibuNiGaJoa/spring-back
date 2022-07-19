package kakao.valuetogether.domain;

import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
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
    private Integer amountDirect = 0;

    @Column(name = "cheer_donation")
    private Integer amountCheer = 0;

    @Column(name = "share_donation")
    private Integer amountShare = 0;

    @Column(name = "comment_donation")
    private Integer amountComment = 0;

    @Column(name = "count_direct_donation")
    private Integer countDirect = 0;

    @Column(name = "count_cheer_donation")
    private Integer countCheer = 0;

    @Column(name = "count_share_donation")
    private Integer countShare = 0;

    @Column(name = "count_comment_donation")
    private Integer countComment = 0;

    public Donation(Member member, Post post) {
        this.member = member;
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

    public void addCountCheer() {
        this.countCheer += 1;
    }

    public void addCountShare() {
        this.countShare += 1;
    }

    public void addCountComment() {
        this.countComment += 1;
    }

    public Integer getTotalAmount() {
        return this.getAmountDirect() +
                this.getAmountCheer() +
                this.getAmountComment() +
                this.getAmountShare();
    }

}
