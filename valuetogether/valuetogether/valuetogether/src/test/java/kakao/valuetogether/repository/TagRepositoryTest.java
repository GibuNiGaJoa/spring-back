package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Tag;
import kakao.valuetogether.domain.enums.Target;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
//@Rollback(value = false)
public class TagRepositoryTest {
    @Autowired
    TagRepository tagRepository;

    @Test
    public void findTopic() {
        List<Tag> findTopic = tagRepository.findTopic();
        for (int i = 0; i < findTopic.size(); i++) {
            findTopic.get(i).getTagName();
        }
    }
    @Test
    public void findTarget() {
        List<Tag> findTarget = tagRepository.findTarget();
        for (int i = 0; i < findTarget.size(); i++) {
            findTarget.get(i).getTagName();
        }
    }
}
