package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;

@Repository
public class TagRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(){

        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(new Tag("어려운이웃"));
        tags.add(new Tag("행복한노후"));
        tags.add(new Tag("여성인권"));
        tags.add(new Tag("심리상담"));
        tags.add(new Tag("환경교육"));
        tags.add(new Tag("학대아동지원"));
        tags.add(new Tag("환경을위한실천"));
        tags.add(new Tag("우크라이나긴급모금"));
        tags.add(new Tag("세상을바꾸는여성"));
        tags.add(new Tag("언택트프로젝트"));

        for (int i = 0; i < tags.size(); i++) {
            em.persist(tags.get(i));
        }

        em.flush();
        em.clear();
    }
}
