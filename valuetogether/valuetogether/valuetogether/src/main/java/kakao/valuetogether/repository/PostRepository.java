package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Post;

import kakao.valuetogether.domain.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PostRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Post post) {
        em.persist(post);
        return post.getId();
    }

    public Post findOneById(Long id) {
        return em.find(Post.class, id);
    }

    public List<Post> findAllRandom() {
        return em.createQuery("select distinct p from Post p where p.endDate>=now() order by rand()", Post.class)
                .getResultList();
    }

    public List<Post> findAllNew() {
        return em.createQuery("select distinct p from Post p where p.endDate>=now() order by p.startDate desc", Post.class)
                .getResultList();
    }

    public List<Post> findAllEnd() {
        return em.createQuery("select distinct p from Post p where p.endDate>=now() order by p.endDate", Post.class)
                .getResultList();
    }

    //키워드로 게시글 조회(제목+본문)
    public List<Post> findPostByKeyword(String keyword) {
        return em.createQuery("select p from Post p where p.title like :keyword or p.content like :keyword order by p.endDate", Post.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getResultList();
    }

    //키워드로 게시글 조회(제목만)
    public List<Post> findPostByTileKeyword(String keyword) {
        return em.createQuery("select p from Post p where p.title like :keyword order by p.endDate", Post.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getResultList();
    }

    public void delete(Post post) {
        em.remove(post);
    }
}
