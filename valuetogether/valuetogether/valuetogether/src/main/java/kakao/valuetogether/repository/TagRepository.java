package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Tag;
import kakao.valuetogether.domain.TagPost;
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

    //태그10개 랜덤으로 조회하기
    public List<Tag> findTen() {
        return em.createQuery("select t from Tag t order by rand()", Tag.class)
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultList();

    }

    //이름으로 태그조회하기
    public Optional<Tag> findByFullName(String name) {
        List<Tag> findTag = em.createQuery("select t from Tag t where t.tagName = :tagName", Tag.class)
                .setParameter("tagName", name)
                .getResultList();

        return findTag.stream().findAny();
    }

    public List<Tag> findTagByKeyword(String keyword) {
        return em.createQuery("select t from Tag t where t.tagName like :keyword", Tag.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getResultList();
    }
}
