package kakao.valuetogether.service;

import kakao.valuetogether.domain.Comment;
import kakao.valuetogether.domain.LikeDetail;
import kakao.valuetogether.domain.Member;
import kakao.valuetogether.repository.LikeDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeDetailService {

    private final LikeDetailRepository likeDetailRepository;

    public Long save(LikeDetail likeDetail) {
        return likeDetailRepository.save(likeDetail);
    }

    public void delete(LikeDetail likeDetail) {
        likeDetailRepository.delete(likeDetail);
    }

    public LikeDetail findOne(Comment comment, Member member) {
        Optional<LikeDetail> findLikeDetail = likeDetailRepository.findOne(comment, member);
        return findLikeDetail.get();
    }
}
