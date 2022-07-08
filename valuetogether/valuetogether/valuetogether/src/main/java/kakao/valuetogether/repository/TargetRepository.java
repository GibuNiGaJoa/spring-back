package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Target;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class TargetRepository {

    @PersistenceContext
    private EntityManager em;

    public void save() {
        Target target1 = new Target("아동|청소년");
        Target target2 = new Target("청년");
        Target target3 = new Target("여성");
        Target target4 = new Target("실버세대");
        Target target5 = new Target("장애인");
        Target target6 = new Target("이주민|다문화");
        Target target7 = new Target("지구촌");
        Target target8 = new Target("어려운이웃");
        Target target9 = new Target("우리사회");
        Target target10 = new Target("유기동물");
        Target target11 = new Target("야생동물");

        em.persist(target1);
        em.persist(target2);
        em.persist(target3);
        em.persist(target4);
        em.persist(target5);
        em.persist(target6);
        em.persist(target7);
        em.persist(target8);
        em.persist(target9);
        em.persist(target10);
        em.persist(target11);

        em.flush();
        em.clear();
    }
}
