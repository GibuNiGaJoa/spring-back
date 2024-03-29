package kakao.valuetogether.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Member  {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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

//    public Member(String email,
//                  String pw,
//                  String name,
//                  String phone,
//                  String address,
//                  String gender,
//                  String nickname,
//                  String birthday) {
//        this.email = email;
//        this.pw = pw;
//        this.name = name;
//        this.phone = phone;
//        this.address = address;
//        this.gender = gender;
//        this.nickname = nickname;
//        this.birthday = birthday;
//    }

    public Member() {

    }

    public Member(String email, String pw, String name, String phone, String address, String gender, String nickname, String birthday) {
        this.email = email;
        this.pw = pw;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.nickname = nickname;
        this.birthday = birthday;
    }
}