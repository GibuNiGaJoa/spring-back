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

@RestController
@RequiredArgsConstructor
public class DonationApiController {

    private final DonationService donationService;
    private final JwtService jwtService;

    // TODO: 기부내역 총 금액 추가

    /**
     * @param token
     * @param request: postId, donationType, donationDate, donationAmount
     */
    @PostMapping("/donate")
    public void donate(@RequestHeader(value = "Authorization") String token, @RequestBody @Valid DonationRequestDTO request) {
        Long memberId = jwtService.parseJwtToken("Bearer " + token);
        donationService.donate(request, memberId);
    }

    @GetMapping("/my/donations")
    public List<DonationResponseDTO> requestDonationDetails(@RequestHeader(value = "Authorization") String token) {
        Long memberId = jwtService.parseJwtToken("Bearer " + token);
        List<DonationResponseDTO> result = donationService.findDonationDetailByMember(memberId);
        return result;
    }

}