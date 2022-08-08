package kakao.valuetogether.service;

import kakao.valuetogether.domain.Link;
import kakao.valuetogether.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LinkService {

    private final LinkRepository linkRepository;

    public Long save(Link link) {
        Long linkSavedId = linkRepository.save(link);

        return linkSavedId;
    }
}
