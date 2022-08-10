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

    public List<Post> findAllRandomByCategory(Tag tag) {
        return tagPostRepository.findAllRandomByCategory(tag);
    }

    public List<Post> findAllNewByCategory(Tag tag) {
        return tagPostRepository.findAllNewByCategory(tag);
    }

    public List<Post> findAllEndByCategory(Tag tag) {
        return tagPostRepository.findAllEndByCategory(tag);
    }

    public List<Tag> findTagByPost(Post post) {
        return tagPostRepository.findTagByPost(post);
    }


}
