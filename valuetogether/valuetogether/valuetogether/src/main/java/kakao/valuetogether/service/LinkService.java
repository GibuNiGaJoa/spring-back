package kakao.valuetogether.service;

import kakao.valuetogether.domain.Link;
import kakao.valuetogether.domain.Post;
import kakao.valuetogether.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LinkService {

    private final LinkRepository linkRepository;

    public Long save(Link link) {
        Long linkSavedId = linkRepository.save(link);

        return linkSavedId;
    }

    public List<Link> findLinkByPost(Post post) {
        return linkRepository.findLinkByPost(post);
    }
}
