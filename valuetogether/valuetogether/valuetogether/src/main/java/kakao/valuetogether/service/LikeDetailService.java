package kakao.valuetogether.service;

import kakao.valuetogether.domain.LikeDetail;
import kakao.valuetogether.repository.LikeDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeDetailService {

    private final LikeDetailRepository likeDetailRepository;

    public Long save(LikeDetail likeDetail) {
        return likeDetailRepository.save(likeDetail);
    }
}
