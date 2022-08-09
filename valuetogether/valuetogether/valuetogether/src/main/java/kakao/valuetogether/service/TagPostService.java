package kakao.valuetogether.service;

import kakao.valuetogether.domain.TagPost;
import kakao.valuetogether.repository.TagPostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    //public TagPost find
}
