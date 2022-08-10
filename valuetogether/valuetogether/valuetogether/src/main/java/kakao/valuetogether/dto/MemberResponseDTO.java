package kakao.valuetogether.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponseDTO {
    private Long id;
    private String email;
    private String pw;
    private String name;
    private String phone;
    private String address;
    private String gender;
    private String nickname;
    private String birthday;
}
