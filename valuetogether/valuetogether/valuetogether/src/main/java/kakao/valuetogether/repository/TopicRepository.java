package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Topic;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class TopicRepository {

    @PersistenceContext
    private EntityManager em;

    public void save() {
        Topic topic1 = new Topic("모두의교육");
        Topic topic2 = new Topic("기본생활지원");
        Topic topic3 = new Topic("안정된일자리");
        Topic topic4 = new Topic("건강한삶");
        Topic topic5 = new Topic("인권평화와역사");
        Topic topic6 = new Topic("동물");
        Topic topic7 = new Topic("지역공동체");
        Topic topic8 = new Topic("더나은사회");
        Topic topic9 = new Topic("환경");

        em.persist(topic1);
        em.persist(topic2);
        em.persist(topic3);
        em.persist(topic4);
        em.persist(topic5);
        em.persist(topic6);
        em.persist(topic7);
        em.persist(topic8);
        em.persist(topic9);

        em.flush();
        em.clear();
    }


}
