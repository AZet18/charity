package pl.coderslab.charity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.repository.DonationRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DonationService {

    private final DonationRepository donationRepository;

    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    public Donation getDonationById(Long id) {
        Optional<Donation> optionalDonation = donationRepository.findById(id);
        return optionalDonation.orElseThrow(() -> new RuntimeException("Donation not found " + id));
    }

    public Donation saveDonation(Donation donation) {
        return donationRepository.save(donation);
    }

    public void deleteDonation(Long id) {
        donationRepository.deleteById(id);
    }

    public Integer getTotalQuantity() {
        return donationRepository.sumQuantity();
    }

    public Long getTotalDonation() {
        return donationRepository.count();
    }
}
