package kakao.valuetogether.api;

import kakao.valuetogether.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ImageApiController {

    private final AwsS3Service awsS3Service;

    @PostMapping("/upload")
    public String uploadImg(@RequestParam("img") MultipartFile imageFile) {
        return awsS3Service.uploadFileV1(imageFile);
    }
}
