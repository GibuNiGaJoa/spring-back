package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TagRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Tag tag) {
        em.persist(tag);
        return tag.getId();
    }

    //이름으로 태그조회하기
    public Optional<Tag> findByName(String name) {
        List<Tag> findTag = em.createQuery("select t from Tag t where t.tagName = :tagName", Tag.class)
                .setParameter("tagName", name)
                .getResultList();

        return findTag.stream().findAny();
    }

    //주제에 해당하는 태그값 조회하기
    public List<Tag> findTopic() {
        return em.createQuery("select t from Tag t", Tag.class)
                .setFirstResult(0)
                .setMaxResults(9)
                .getResultList();
    }
    //대상에 해당하는 태그값 조회하기
    public List<Tag> findTarget() {
        return em.createQuery("select t from Tag t", Tag.class)
                .setFirstResult(9)
                .setMaxResults(11)
                .getResultList();
    }
}
