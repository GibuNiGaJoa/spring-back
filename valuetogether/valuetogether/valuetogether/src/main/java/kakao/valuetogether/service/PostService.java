package kakao.valuetogether.service;

import kakao.valuetogether.domain.Post;
import kakao.valuetogether.domain.Target;
import kakao.valuetogether.domain.Topic;
import kakao.valuetogether.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    LinkRepository linkRepository;

    //제안하기
    public Long propose(Post post) {
        Long postSavedId = postRepository.save(post);
        return postSavedId;
    }

    //주제,대상,태그 기본세팅
    public void basicSetting() {
        tagRepository.save();
    }

    //주제별 게시글 모두 조회하기
    public List<Post> searchByTopic(Topic topic) {
        return postRepository.findAllByTopic(topic);
    }

    //대상별 게시글 모두 조회하기
    public List<Post> searchByTarget(Target target) {
        return postRepository.findAllByTarget(target);
    }
}
