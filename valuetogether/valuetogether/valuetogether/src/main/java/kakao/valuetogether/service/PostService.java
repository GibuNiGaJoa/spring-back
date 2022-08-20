package kakao.valuetogether.service;

import kakao.valuetogether.domain.Post;
import kakao.valuetogether.domain.Tag;
import kakao.valuetogether.domain.TagPost;

import kakao.valuetogether.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
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

    public Post findOneById(Long id) {
        return postRepository.findOneById(id);
    }

    public List<Post> findAllRandom() {
        return postRepository.findAllRandom();
    }
    public List<Post> findAllNew() {
        return postRepository.findAllNew();
    }
    public List<Post> findAllEnd() {
        return postRepository.findAllEnd();
    }

    public List<Post> findPostByKeyword(String keyword) {
        return postRepository.findPostByKeyword(keyword);
    }

    public List<Post> findPostByTileKeyword(String keyword) {
        return postRepository.findPostByTileKeyword(keyword);
    }

}
