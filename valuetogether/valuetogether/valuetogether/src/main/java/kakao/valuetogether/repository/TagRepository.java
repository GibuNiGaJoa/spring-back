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

    public Tag findOne(Long id) {
        return em.find(Tag.class, id);
    }

    //이름으로 태그조회하기
    public Optional<Tag> findByName(String name) {
        List<Tag> findTag = em.createQuery("select t from Tag t where t.tagName = :tagName", Tag.class)
                .setParameter("tagName", name)
                .getResultList();

        return findTag.stream().findAny();
    }

}
