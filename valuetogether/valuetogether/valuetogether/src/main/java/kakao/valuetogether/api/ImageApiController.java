package kakao.valuetogether.api;

import kakao.valuetogether.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ImageApiController {

    private final AwsS3Service awsS3Service;
//    private final String local = "C:\\Users\\kimdongho\\Desktop\\spring-back\\valuetogether\\valuetogether\\valuetogether\\src\\main\\resources\\static\\image\\";

    @PostMapping("/test")
    public String uploadImg(@RequestParam("img") MultipartFile imageFile) throws IOException {
        return awsS3Service.uploadFileV1(imageFile);
//        imageFile.transferTo(new File(local + imageFile.getOriginalFilename()));
//        return "http://localhost:8080/image/" + imageFile.getOriginalFilename();

//        imageFile.transferTo(new File("/home/ubuntu/app/valuetogether/valuetogether/valuetogether/src/main/resources/static/image" + imageFile.getOriginalFilename()));
//        return "http://valuetogether.tk/image/" + imageFile.getOriginalFilename();
    }
}
