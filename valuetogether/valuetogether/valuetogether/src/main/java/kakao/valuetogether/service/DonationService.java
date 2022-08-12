package kakao.valuetogether.service;

import kakao.valuetogether.domain.Donation;
import kakao.valuetogether.domain.DonationDetail;
import kakao.valuetogether.dto.DonationRequestDTO;
import kakao.valuetogether.repository.DonationDetailRepository;
import kakao.valuetogether.repository.DonationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DonationService {

    private final DonationRepository donationRepository;
    private final DonationDetailRepository donationDetailRepository;

    public void createDonation(DonationRequestDTO request) {
        Donation donation = new Donation(request.getPost());
        donationRepository.createDonation(donation);
    }

    public void donate(DonationRequestDTO request) {
        Donation findDonation = donationRepository.findByPostId(request.getPost().getId());
        findDonation.donate(request.getDonationType(), request.getDonationAmount());
        donationRepository.updateDonation(findDonation);

        DonationDetail donationDetail = DonationDetail.builder()
                .member(request.getMember())
                .post(request.getPost())
                .donationType(request.getDonationType())
                .donationAmount(request.getDonationAmount())
                .donationDate(request.getDonationDate()).build();
        donationDetailRepository.createDonationDetail(donationDetail);
    }

    public Donation findDonationByPost(DonationRequestDTO request) {
        return donationRepository.findByPostId(request.getPost().getId());
    }

    public DonationDetail findDonationDetailByMember(DonationRequestDTO request) {
        return donationDetailRepository.findDonationDetailByMember(request.getMember());
    }

    public void removeDonation(DonationRequestDTO request) {
        Donation findDonation = donationRepository.findByPostId(request.getPost().getId());
        donationRepository.deleteDonation(findDonation);
    }
}
