package kakao.valuetogether.service;

import kakao.valuetogether.domain.Post;
import kakao.valuetogether.domain.Tag;
import kakao.valuetogether.domain.TagPost;
import kakao.valuetogether.domain.enums.Target;
import kakao.valuetogether.domain.enums.Topic;
import kakao.valuetogether.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final LinkRepository linkRepository;

    private final TagPostRepository tagPostRepository;
    public Long propose(Post post) {
        Long postSavedId = postRepository.save(post);

        return postSavedId;
    }

//    public List<Post> searchByTopic(Topic topic) {
//        return postRepository.findAllByTopic(topic);
//    }
//
//    public List<Post> searchByTarget(Target target) {
//        return postRepository.findAllByTarget(target);
//    }
}
