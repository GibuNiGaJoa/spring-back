package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class TagRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(){
        Tag tag1 = new Tag("어려운이웃");
        Tag tag2 = new Tag("행복한노후");
        Tag tag3 = new Tag("여성인권");
        Tag tag4 = new Tag("심리상담");
        Tag tag5 = new Tag("환경교육");
        Tag tag6 = new Tag("학대아동지원");
        Tag tag7 = new Tag("환경을위한실천");
        Tag tag8 = new Tag("우크라이나긴급모금");
        Tag tag9 = new Tag("세상을바꾸는여성");
        Tag tag10 = new Tag("언택트프로젝트");

        em.persist(tag1);
        em.persist(tag2);
        em.persist(tag3);
        em.persist(tag4);
        em.persist(tag5);
        em.persist(tag6);
        em.persist(tag7);
        em.persist(tag8);
        em.persist(tag9);
        em.persist(tag10);

        em.flush();
        em.clear();
    }
}
