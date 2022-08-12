package kakao.valuetogether.repository;

import kakao.valuetogether.domain.Post;
import kakao.valuetogether.domain.Tag;
import kakao.valuetogether.domain.TagPost;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class TagPostRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(TagPost tagPost) {
        em.persist(tagPost);
        return tagPost.getId();
    }

    public Optional<TagPost> findById(Long id) {
        List<TagPost> findTagPost = em.createQuery("select t from TagPost t where t.id = :id")
                .setParameter("id", id)
                .getResultList();
        return findTagPost.stream().findAny();
    }

    public List<Post> findAllPostRandomByCategory(Tag tag) {
        List<Post> postList = em.createQuery("select p from TagPost t inner join t.post p on t.tag = :tag and t.post = p.id and p.endDate>=now() order by rand()", Post.class)
                .setParameter("tag", tag)
                .getResultList();
        return postList;
    }
    public List<Post> findAllPostNewByCategory(Tag tag) {
        List<Post> postList = em.createQuery("select p from TagPost t inner join t.post p on t.tag = :tag and t.post = p.id and p.endDate>=now() order by p.startDate desc", Post.class)
                .setParameter("tag", tag)
                .getResultList();
        return postList;
    }
    public List<Post> findAllPostEndByCategory(Tag tag) {
        List<Post> postList = em.createQuery("select p from TagPost t inner join t.post p on t.tag = :tag and t.post = p.id and p.endDate>=now() order by p.endDate", Post.class)
                .setParameter("tag", tag)
                .getResultList();
        return postList;
    }

    public List<Tag> findTagByPost(Post post) {
        List<Tag> tagList = em.createQuery("select t.tag from TagPost t where t.post = :post and t.tag.id > 20", Tag.class)
                .setParameter("post", post)
                .getResultList();
        return tagList;
    }

    public List<Post> findAllPostByTag(Tag tag) {
        List<Post> postList = em.createQuery("select t.post from TagPost t where t.tag =:tag order by t.post.endDate", Post.class)
                .setParameter("tag", tag)
                .getResultList();
        return postList;
    }

    public List<Post> findNowPostByTag(Tag tag) {
        List<Post> postList = em.createQuery("select t.post from TagPost t where t.tag =:tag and t.post.endDate>=now() order by t.post.endDate", Post.class)
                .setParameter("tag", tag)
                .getResultList();
        return postList;
    }

    public List<Post> findEndPostByTag(Tag tag) {
        List<Post> postList = em.createQuery("select t.post from TagPost t where t.tag =:tag and t.post.endDate<=now() order by t.post.endDate", Post.class)
                .setParameter("tag", tag)
                .getResultList();
        return postList;
    }
}
