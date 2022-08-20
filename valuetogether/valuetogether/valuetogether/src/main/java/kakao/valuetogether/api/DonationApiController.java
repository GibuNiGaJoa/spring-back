package kakao.valuetogether.api;

import kakao.valuetogether.domain.DonationDetail;
import kakao.valuetogether.dto.DonationRequestDTO;
import kakao.valuetogether.dto.DonationResponseDTO;
import kakao.valuetogether.service.DonationService;
import kakao.valuetogether.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO
 * 1. (테스트 필요) 게시글 생성 시 기부 생성 요청 API 0-> PostApiController.proposePost()
 * 2. (완료) 기부하기 API -> @param: postId, memberId, donationType, donationAmount, donationDate
 * 3. (완료) 기부 조회 API -> PostApiController.findPost()
 * 4. (완료) 기부내역 조회 API -> @param: memberId
 *
 * 6. 1번 테스트, 2번 토큰 추가,
 */

@RestController
@RequiredArgsConstructor
public class DonationApiController {

    private final DonationService donationService;
    private final JwtService jwtService;

    @PostMapping("/donate")
    public ResponseEntity donate(@RequestHeader(value = "Authorization") String token, @RequestBody @Valid DonationRequestDTO request) {
        Long memberId = jwtService.parseJwtToken("Bearer " + token);

        donationService.donate(request, memberId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/my/donations")
    public List<DonationResponseDTO> requestDonationDetails(@RequestBody @Valid DonationRequestDTO request) {
        List<DonationResponseDTO> result = donationService.findDonationDetailByMember(request);
        return result;
    }

}