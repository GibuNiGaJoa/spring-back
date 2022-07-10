package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Tag;
import kakao.valuetogether.domain.Target;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;

@Repository
public class TargetRepository {

    @PersistenceContext
    private EntityManager em;

    public void save() {

        ArrayList<Target> targets = new ArrayList<>();
        targets.add(new Target("아동|청소년"));
        targets.add(new Target("청년"));
        targets.add(new Target("여성"));
        targets.add(new Target("실버세대"));
        targets.add(new Target("장애인"));
        targets.add(new Target("이주민|다문화"));
        targets.add(new Target("지구촌"));
        targets.add(new Target("어려운이웃"));
        targets.add(new Target("우리사회"));
        targets.add(new Target("유기동물"));
        targets.add(new Target("야생동물"));

        for (int i = 0; i < targets.size(); i++) {
            em.persist(targets.get(i));
        }

        em.flush();
        em.clear();
    }
}
