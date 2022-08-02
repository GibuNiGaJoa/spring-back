package kakao.valuetogether.api;

import kakao.valuetogether.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

// String 받는 메소드 -> base64 추출하는 메소드 -> base64를 저장하고 URL 반환하는 메소드

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

//    @PostMapping("/test")
//    public String saveProfileImage(MultipartFile imageFile) throws Exception {
//        String imagePath = null;
//        String absolutePath = new File("").getAbsolutePath() + "\\";
//        String path = "resources/image";
//
//        File file = new File(path);
//        if (!file.exists())
//            file.mkdirs();
//
//        if(imageFile == null)
//            return "null입니다.";
//
//        if (!imageFile.isEmpty()) {
//            String contentType = imageFile.getContentType();
//            String originalFileExtension;
//
//            if (ObjectUtils.isEmpty(contentType)) {
//                throw new Exception("이미지 파일은 jpg, png 만 가능합니다.");
//            } else {
//                if (contentType.contains("image/jpeg")) {
//                    originalFileExtension = ".jpg";
//                } else if (contentType.contains("image/png")) {
//                    originalFileExtension = ".png";
//                } else {
//                    throw new Exception("이미지 파일은 jpg, png 만 가능합니다.");
//                }
//            }
//
//            imagePath = path + "/" + originalFileExtension;
//            file = new File(absolutePath + imagePath);
//            imageFile.transferTo(file);
//        }
//        else {
//            throw new Exception("이미지 파일이 비어있습니다.");
//        }
//
//        return imagePath;
//    }

    @PostMapping("/test")
    public String upload(@RequestParam("img") MultipartFile imageFile) throws IOException {
//        imageFile.transferTo(new File("C:\\Users\\kimdongho\\Desktop\\spring-back\\valuetogether\\valuetogether\\valuetogether\\src\\main\\resources\\static\\image\\" + imageFile.getOriginalFilename()));
//        return "http://localhost:8080/image/" + imageFile.getOriginalFilename();

        imageFile.transferTo(new File("/home/ubuntu/app/valuetogether/valuetogether/valuetogether/src/main/resources/static/image/" + imageFile.getOriginalFilename()));
        return "http://valuetogether.tk/image/" + imageFile.getOriginalFilename();
    }
}