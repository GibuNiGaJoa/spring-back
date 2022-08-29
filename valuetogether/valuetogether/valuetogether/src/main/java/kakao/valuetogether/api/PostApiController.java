package kakao.valuetogether.api;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.util.JSONPObject;
import kakao.valuetogether.domain.*;
import kakao.valuetogether.dto.CommentResponseDTO;
import kakao.valuetogether.dto.DonationRequestDTO;
import kakao.valuetogether.dto.DonationResponseDTO;
import kakao.valuetogether.service.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;
    private final MemberService memberService;
    private final TagService tagService;
    private final TagPostService tagPostService;
    private final LinkService linkService;
    private final JwtService jwtService;
    private final DonationService donationService;
    private final CommentService commentService;

    //제안하기 전 로그인검증
    @GetMapping("fundraisings/propose")
    public ProposeResponse propose(@RequestHeader(value = "Authorization") String token) {
        Long findId = jwtService.parseJwtToken("Bearer " + token);//토큰 검증
        Member findMember = memberService.findOne(findId);
        return new ProposeResponse(findMember.getName());
    }
    @Data
    static class ProposeResponse {
        private String proposer;
        public ProposeResponse(String proposer) {
            this.proposer = proposer;
        }
    }

    //기부 제안하기
    @PostMapping("fundraisings/propose/project")
    public CreatedPostResponse proposePost(@RequestHeader(value = "Authorization") String token,
                                           @RequestBody @Valid CreatedPostRequest request) {

        Long memberId = jwtService.parseJwtToken("Bearer " + token);
        Member findMember = memberService.findOne(memberId);

        Post post = new Post();
        post.setMember(findMember);
        post.setTitle(request.getTitle());
        post.setProposer(request.getProposer());
        post.setContent(request.getContent());
        post.setTargetAmount(request.getTargetAmount());
        post.setStartDate(request.getStartDate());
        post.setEndDate(request.getEndDate());
        post.setImage(request.getImage());
        post.setIsConfirm(false);

        Long postId = postService.propose(post);
        Post findPost = postService.findOneById(postId);

        donationService.createDonation(findPost);

        tagPostService.save(new TagPost(tagService.findIdByFullName(request.getTopic()), findPost));
        tagPostService.save(new TagPost(tagService.findIdByFullName(request.getTarget()), findPost));

        request.getTag().forEach(m -> {
            Tag tag = new Tag(m.text);
            tagService.addTag(tag);
            tagPostService.save(new TagPost(tagService.findIdByFullName(m.text), findPost));
        });

        request.getLink().forEach(m -> {
            Link link = new Link(findPost, m.text);
            linkService.save(link);
        });

        return new CreatedPostResponse(true);
    }

    @Data
    static class CreatedPostRequest {
        private String title;
        private String proposer;
        private String content;
        private Integer targetAmount;
        private Date startDate;
        private Date endDate;
        private String topic;
        private String target;
        private List<CreatedPostRequest> tag;
        private List<CreatedPostRequest> link;
        private String image;
        private String id;
        private String text;
    }

    @Data
    static class CreatedPostResponse {
        private Boolean status;

        public CreatedPostResponse(Boolean status) {
            this.status = status;
        }
    }

    //게시글 정보 조회
    @GetMapping("fundraisings/{id}")
    public FindPostResponse findPost(@RequestHeader(value = "Authorization",required = false) String token,
                                     @PathVariable("id") Long postId) {
        Post findPost = postService.findOneById(postId);

        List<Tag> findTags = tagPostService.findTagByPost(findPost);
        List<TagDto> tagList = findTags.stream()
                .map(m -> new TagDto(m.getTagName()))
                .collect(Collectors.toList());

        List<Link> findLinks = linkService.findLinkByPost(findPost);
        List<LinkDto> linkList = findLinks.stream()
                .map(m -> new LinkDto(m.getLink()))
                .collect(Collectors.toList());

        Donation donation = donationService.findDonationByPost(findPost);
        DonationResponseDTO donationResponse = donationService.createDonationResponse(donation);

        List<CommentResponseDTO> commentList;

        Boolean isDonateCheer;

        if (token != null) {
            Long memberId = jwtService.parseJwtToken("Bearer " + token);
            Member findMember = memberService.findOne(memberId);

            commentList = commentService.findComments(findPost, findMember);

            isDonateCheer = donationService.isDonateCheer(memberId, postId);
        }
        else {
            List<Comment> findComments = commentService.findComment(findPost);
            commentList = findComments.stream()
                    .map(m -> new CommentResponseDTO(m.getId(), m.getMember(). getNickname(), m.getContent(), m.getDate(), m.getLikes(),false,false, m.getDonationAmount(),m.getMember().getGender()))
                    .collect(Collectors.toList());

            isDonateCheer = false;
        }

        FindPostResponse findPostResponse = new FindPostResponse(
                findPost.getTitle(), findPost.getProposer(), findPost.getContent(),
                findPost.getTargetAmount(), findPost.getStartDate(),
                findPost.getEndDate(), tagList, linkList, findPost.getImage(),
                donationResponse, commentList, isDonateCheer);
        return findPostResponse;
    }

    @Data
    @AllArgsConstructor
    static class FindPostResponse<T> {
        private String title;
        private String subTitle;
        private String content;
        private Integer targetAmount;
        private Date startDate;
        private Date endDate;
        private T tag;
        private T link;
        private String image;

        private DonationResponseDTO donation;

        private T comment;

        private boolean isDonateCheer;
    }

    @Data
    @AllArgsConstructor
    static class TagDto {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class LinkDto {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class CommentDto {
        private Long id;
        private String nickname;
        private String content;
        private Date date;
        private Integer likes;
        private Integer donationAmount;
    }

    //전체게시글랜덤조회
    @GetMapping("fundraisings/now/sort1")
    public PostResult findAllRandomPost() {

        List<Post> findPosts = postService.findAllRandom();
        List<PostDto> postList = findPosts.stream()
                .map(m -> new PostDto(m.getId(), m.getImage(), m.getTitle(), m.getProposer(), m.getEndDate()))
                .collect(Collectors.toList());

        return new PostResult(postList);
    }

    //전체게시글최신순조회
    @GetMapping("fundraisings/now/sort2")
    public PostResult findAllNewPost() {

        List<Post> findPosts = postService.findAllNew();
        List<PostDto> postList = findPosts.stream()
                .map(m -> new PostDto(m.getId(), m.getImage(), m.getTitle(), m.getProposer(), m.getEndDate()))
                .collect(Collectors.toList());

        return new PostResult(postList);
    }

    //전체게시글종료임박순조회
    @GetMapping("fundraisings/now/sort3")
    public PostResult findAllEndPost() {

        List<Post> findPosts = postService.findAllEnd();
        List<PostDto> postList = findPosts.stream()
                .map(m -> new PostDto(m.getId(), m.getImage(), m.getTitle(), m.getProposer(), m.getEndDate()))
                .collect(Collectors.toList());

        return new PostResult(postList);
    }

    //게시글카테고리별랜덤조회
    @GetMapping("fundraisings/now/sort1/{id}")
    public PostResult findAllPostRandomCategory(@PathVariable("id") Long id) {

        Tag findTag = tagService.findById(id);
        List<Post> findPosts = tagPostService.findAllPostRandomByCategory(findTag);
        List<PostDto> postList = findPosts.stream()
                .map(m -> new PostDto(m.getId(), m.getImage(), m.getTitle(), m.getProposer(), m.getEndDate()))
                .collect(Collectors.toList());

        return new PostResult(postList);
    }

    //전체게시글카테고리별최신순조회
    @GetMapping("fundraisings/now/sort2/{id}")
    public PostResult findAllPostNewCategory(@PathVariable("id") Long id) {

        Tag findTag = tagService.findById(id);
        List<Post> findPosts = tagPostService.findAllPostNewByCategory(findTag);
        List<PostDto> postList = findPosts.stream()
                .map(m -> new PostDto(m.getId(), m.getImage(), m.getTitle(), m.getProposer(), m.getEndDate()))
                .collect(Collectors.toList());

        return new PostResult(postList);
    }

    //전체게시글카테고리별종료임박순조회
    @GetMapping("fundraisings/now/sort3/{id}")
    public PostResult findAllPostEndCategory(@PathVariable("id") Long id) {

        Tag findTag = tagService.findById(id);
        List<Post> findPosts = tagPostService.findAllPostEndByCategory(findTag);
        List<PostDto> postList = findPosts.stream()
                .map(m -> new PostDto(m.getId(), m.getImage(), m.getTitle(), m.getProposer(), m.getEndDate()))
                .collect(Collectors.toList());

        return new PostResult(postList);
    }

    @Data
    @AllArgsConstructor
    static class PostResult<T> {
        private T post;
    }

    @Data
    @AllArgsConstructor
    static class PostDto {
        private Long id;
        private String image;
        private String title;
        private String proposer;
        private Date endDate;
    }

}
