package pl.coderslab.charity.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.repository.DonationRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DonationServiceTest {

    @Mock
    private DonationRepository donationRepository;

    @InjectMocks
    private DonationService donationService;

    @Test
    void getAllDonationsShouldReturnDonationList() {
        Donation firstDonation = new Donation();
        firstDonation.setId(1L);
        Donation secondDonation = new Donation();
        secondDonation.setId(2L);
        when(donationRepository.findAll()).thenReturn(List.of(firstDonation, secondDonation));
        List<Donation> donations = donationService.getAllDonations();

        assertEquals(2, donations.size());
        assertEquals(1L, donations.get(0).getId());
        assertEquals(2L, donations.get(1).getId());
        verify(donationRepository).findAll();
    }

    @Test
    void getDonationByIdShouldReturnDonationIfExist() {
        Long donationId = 1L;
        Donation donation = new Donation();
        donation.setId(1L);
        when(donationRepository.findById(donationId)).thenReturn(Optional.of(donation));
        Donation donationResult = donationService.getDonationById(donationId);
        assertEquals(donation, donationResult);
        verify(donationRepository).findById(donationId);
    }

    @Test
    void getDonationByIdShouldThrowExceptionDonationWhenNotFound() {
        Long donationId = 1L;
        when(donationRepository.findById(donationId)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                donationService.getDonationById(donationId));
        assertEquals("Donation not found " + donationId, exception.getMessage());

    }

    @Test
    void deleteDonationShouldCallDeletedById() {
        Long donationId = 1L;
        donationService.deleteDonation(donationId);
        verify(donationRepository).deleteById(donationId);

    }

    @Test
    void saveDonationShouldReturnSavedDonation() {
        Donation donation = new Donation();
        when(donationRepository.save(donation)).thenReturn(donation);
        Donation donationResult = donationService.saveDonation(donation);
        assertEquals(donation, donationResult);
        verify(donationRepository).save(donation);
    }

    @Test
    void getTotalQuantityShouldReturnSumOfQuantities() {
        Long totalQuantities = 10L;
        when(donationRepository.sumQuantity()).thenReturn(Optional.of(totalQuantities));
        Long quantityResult = donationService.getTotalQuantity();
        assertEquals(totalQuantities, quantityResult);
        verify(donationRepository).sumQuantity();
    }

    @Test
    void getTotalDonationShouldReturnTotalDonationCount() {
        Long donationCount = 100L;
        when(donationRepository.count()).thenReturn(donationCount);
        Long donationResult = donationService.getTotalDonation();
        assertEquals(donationCount, donationResult);
        verify(donationRepository).count();
    }
}
