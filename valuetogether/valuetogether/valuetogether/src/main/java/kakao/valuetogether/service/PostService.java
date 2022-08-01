package kakao.valuetogether.service;

import kakao.valuetogether.domain.Post;
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

    public Long propose(Post post) {
        Long postSavedId = postRepository.save(post);
        return postSavedId;
    }

    // 주제,대상,태그 기본세팅
    public void basicSetting() {
        tagRepository.save();
    }

    public List<Post> searchByTopic(Topic topic) {
        return postRepository.findAllByTopic(topic);
    }

    public List<Post> searchByTarget(Target target) {
        return postRepository.findAllByTarget(target);
    }
}
