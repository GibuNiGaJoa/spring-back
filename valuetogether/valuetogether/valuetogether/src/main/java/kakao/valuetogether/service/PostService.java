package kakao.valuetogether.service;

import kakao.valuetogether.domain.Post;
import kakao.valuetogether.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    TargetRepository targetRepository;

    @Autowired
    TopicRepository topicRepository;

    //제안하기
    public Long propose(Post post) {
        Long postSavedId = postRepository.save(post);
        return postSavedId;
    }

    //주제,대상,태그 기본세팅
    public void basicSetting() {
        tagRepository.save();
        topicRepository.save();
        targetRepository.save();
    }
}
