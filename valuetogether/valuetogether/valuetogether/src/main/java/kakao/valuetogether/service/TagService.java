package kakao.valuetogether.service;

import kakao.valuetogether.domain.Tag;
import kakao.valuetogether.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }
    //태그 추가하기
    public void addTag(Tag tag) {
        Optional<Tag> findTag = tagRepository.findByName(tag.getTagName());
        if (findTag.isEmpty()) {
            tagRepository.save(tag);
        }
    }
    //이름으로 조회하기
    public Tag findIdByName(String name) {
        Optional<Tag> findTag = tagRepository.findByName(name);
        return findTag.get();
    }

    public List<Tag> findTopic() {
        return tagRepository.findTopic();
    }

    public List<Tag> findTarget() {
        return tagRepository.findTarget();
    }

    public List<Tag> findTopicTarget() {
        return tagRepository.findTopicTarget();
    }
}
