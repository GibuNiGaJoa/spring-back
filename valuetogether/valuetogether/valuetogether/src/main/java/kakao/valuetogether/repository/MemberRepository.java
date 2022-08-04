package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Member;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public void clearStore() {
        em.clear();
    }

    //회원 저장하기
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    //이메일과 pw로 조회
    public Optional<Member> findByEmailAndPw(String email, String pw) {
        List<Member> findMember = em.createQuery("select m from Member m where m.email = :email and " +
                        "m.pw = :pw")
                .setParameter("email",email)
                .setParameter("pw", pw)
                .getResultList();
        return findMember.stream().findAny();
    }
    public void delete(Long id) {
        Member findMember = em.find(Member.class, id);
        em.remove(findMember);
    }

    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    //이메일로 조회
    public Optional<Member> findByEmail(String email) {
        List<Member> findMember = em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList();
        return findMember.stream().findAny();
    }

    //휴대폰 번호로 조회
    public Optional<Member> findByPhone(String phone) {
        List<Member> findMember = em.createQuery("select m from Member m where m.phone = :phone", Member.class)
                .setParameter("phone", phone)
                .getResultList();
        return findMember.stream().findAny();
    }

    //닉네임 또는 이름과 폰번호로 조회
    public Optional<Member> findByNNP(String nickname, String name, String phone){
        List<Member> findMember = em.createQuery("select m from Member m where (m.nickname = :nickname or " +
                        "m.name = :name) and m.phone = :phone")
                .setParameter("nickname", nickname)
                .setParameter("name", name)
                .setParameter("phone", phone)
                .getResultList();
        return findMember.stream().findAny();
    }

    //이메일 또는 전화번호로 조회
    public Optional<Member> findByEP(String email, String phone){
        List<Member> findMember = em.createQuery("select m from Member m where m.email = :email and " +
                        "m.phone = :phone")
                .setParameter("email",email)
                .setParameter("phone", phone)
                .getResultList();
        return findMember.stream().findAny();
    }

}