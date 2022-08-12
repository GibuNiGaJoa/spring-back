package kakao.valuetogether.service;

import kakao.valuetogether.domain.Post;
import kakao.valuetogether.domain.Tag;
import kakao.valuetogether.domain.TagPost;
import kakao.valuetogether.repository.TagPostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TagPostService {

    private final TagPostRepository tagPostRepository;

    public TagPostService(TagPostRepository tagPostRepository) {
        this.tagPostRepository = tagPostRepository;
    }

    public Long save(TagPost tagPost) {
        return tagPostRepository.save(tagPost);
    }

    public List<Post> findAllPostRandomByCategory(Tag tag) {
        return tagPostRepository.findAllPostRandomByCategory(tag);
    }

    public List<Post> findAllPostNewByCategory(Tag tag) {
        return tagPostRepository.findAllPostNewByCategory(tag);
    }

    public List<Post> findAllPostEndByCategory(Tag tag) {
        return tagPostRepository.findAllPostEndByCategory(tag);
    }

    public List<Tag> findTagByPost(Post post) {
        return tagPostRepository.findTagByPost(post);
    }

    public List<Post> findAllPostByTag(Tag tag) {
        return tagPostRepository.findAllPostByTag(tag);
    }

    public List<Post> findNowPostByTag(Tag tag) {
        return tagPostRepository.findNowPostByTag(tag);
    }
    public List<Post> findEndPostByTag(Tag tag) {
        return tagPostRepository.findEndPostByTag(tag);
    }


}
