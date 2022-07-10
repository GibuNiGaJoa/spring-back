package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Tag;
import kakao.valuetogether.domain.Topic;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;

@Repository
public class TopicRepository {

    @PersistenceContext
    private EntityManager em;

    public void save() {

        ArrayList<Topic> topics = new ArrayList<>();
        topics.add(new Topic("모두의교육"));
        topics.add(new Topic("기본생활지원"));
        topics.add(new Topic("안정된일자리"));
        topics.add(new Topic("건강한삶"));
        topics.add(new Topic("인권평화와역사"));
        topics.add(new Topic("동물"));
        topics.add(new Topic("지역공동체"));
        topics.add(new Topic("더나은사회"));
        topics.add(new Topic("환경"));

        for (int i = 0; i < topics.size(); i++) {
            em.persist(topics.get(i));
        }

        em.flush();
        em.clear();
    }


}
