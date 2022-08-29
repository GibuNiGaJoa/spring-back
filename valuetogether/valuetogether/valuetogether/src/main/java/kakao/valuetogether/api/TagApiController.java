package kakao.valuetogether.api;

import kakao.valuetogether.domain.Post;
import kakao.valuetogether.domain.Tag;
import kakao.valuetogether.service.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagApiController {

    private final TagService tagService;
    private final TagPostService tagPostService;

//    //태그키워드로 게시글조회
//    @RequestMapping(value = "/{tagName}",method = RequestMethod.GET)
//    public PostResult searchPostByTag(@PathVariable("tagName") String tagName) {
//        Tag findTag = tagService.findIdByFullName(tagName);
//        List<Post> findPosts = tagPostService.findAllPostByTag(findTag);
//        List<PostDto> postList = findPosts.stream()
//                .map(m -> new PostDto(m.getId(), m.getImage(), m.getTitle(), m.getProposer(), m.getEndDate()))
//                .collect(Collectors.toList());
//
//        return new PostResult(postList);
//    }

    @RequestMapping(value = "/{tagName}", method = RequestMethod.GET)
    public PostResult searchPostByTagPhase(@PathVariable("tagName") String tagName, @RequestParam(name = "phase", required = false) Long number) {
        Tag findTag = tagService.findIdByFullName(tagName);

        List<Post> findPosts;
        List<PostDto> postDtos = new ArrayList<>();

        AtomicInteger totalDonationAmount = new AtomicInteger(0);
        AtomicInteger totalDonationCount = new AtomicInteger(0);

        if (number == null)
            findPosts = tagPostService.findAllPostByTag(findTag);
        else if (number == 2)
            findPosts = tagPostService.findNowPostByTag(findTag);
        else
            findPosts = tagPostService.findEndPostByTag(findTag);

        findPosts.forEach(post -> {
            PostDto postDto = PostDto.builder()
                    .id(post.getId())
                    .image(post.getImage())
                    .title(post.getTitle())
                    .proposer(post.getProposer())
                    .endDate(post.getEndDate())
                    .build();
            postDtos.add(postDto);
        });

        List<Post> allPostsByTag = tagPostService.findAllPostByTag(findTag);
        allPostsByTag.forEach(post -> {
            totalDonationAmount.addAndGet(post.getDonation().getTotalAmount());
            totalDonationCount.addAndGet(post.getDonation().getTotalCount());
        });

        PostResult result = PostResult.builder()
                .posts(postDtos)
                .totalDonationAmount(totalDonationAmount.intValue())
                .totalDonationCount(totalDonationCount.intValue()).build();
        return result;
    }

    @Data
    @Builder
    static class PostResult {
        private List<PostDto> posts;

        private Integer totalDonationAmount;
        private Integer totalDonationCount;
    }

    @Data
    @Builder
    static class PostDto {
        private Long id;
        private String image;
        private String title;
        private String proposer;
        private Date endDate;
    }

}
