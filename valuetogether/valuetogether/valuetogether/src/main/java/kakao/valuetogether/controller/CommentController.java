package kakao.valuetogether.controller;

import kakao.valuetogether.domain.Comment;
import kakao.valuetogether.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/v1/members")
    public Long enrollCommentV1(@RequestBody @Valid Comment comment) {
        return commentService.enrollComment(comment);
    }
}
