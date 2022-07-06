package kakao.valuetogether.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, name = "member_email")
    private String email;

    @Column(nullable = false, name = "member_pw")
    private String pw;

    @Column(nullable = false, name = "member_name")
    private String name;

    @Column(name = "member_phone")
    private String phone;

    @Column(nullable = false, name = "member_address")
    private String address;

    @Column(nullable = false, name = "member_gender")
    private String gender;

    @Column(nullable = false, name = "member_nickname")
    private String nickname;

    @Column(nullable = false, name = "member_birthday")
    private String birthday;

    public Member(String email,
                  String pw,
                  String name,
                  String phone,
                  String address,
                  String gender,
                  String nickname,
                  String birthday) {
        this.email = email;
        this.pw = pw;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.nickname = nickname;
        this.birthday = birthday;
    }

    public Member() {

    }
}