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

    private static final Long DONATION_ID_1 = 1L;
    private static final Long DONATION_ID_2 = 2L;
    private static final Long DONATION_COUNT = 10L;
    private static final Long DONATION_QUANTITIES = 100L;

    @Mock
    private DonationRepository donationRepository;

    @InjectMocks
    private DonationService donationService;

    @Test
    void getAllDonationsShouldReturnDonationList() {
        when(donationRepository.findAll()).thenReturn(getDonations());
        List<Donation> donations = donationService.getAllDonations();
        assertEquals(2, donations.size());
        assertEquals(DONATION_ID_1, donations.get(0).getId());
        assertEquals(DONATION_ID_2, donations.get(1).getId());
        verify(donationRepository).findAll();
    }

    @Test
    void getDonationByIdShouldReturnDonationIfExists() {
        Donation expectedDonation = getDonation(DONATION_ID_1);
        when(donationRepository.findById(DONATION_ID_1)).thenReturn(Optional.of(expectedDonation));
        Donation actualDonation = donationService.getDonationById(DONATION_ID_1);
        assertDonationEquals(expectedDonation, actualDonation);
        verify(donationRepository).findById(DONATION_ID_1);
    }

    @Test
    void getDonationByIdShouldThrowExceptionDonationWhenNotFound() {
        when(donationRepository.findById(DONATION_ID_1)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                donationService.getDonationById(DONATION_ID_1));
        assertEquals("Donation not found " + DONATION_ID_1, exception.getMessage());

    }

    @Test
    void deleteDonationShouldCallDeletedById() {
        donationService.deleteDonation(DONATION_ID_1);
        verify(donationRepository).deleteById(DONATION_ID_1);

    }

    @Test
    void saveDonationShouldReturnSavedDonation() {
        Donation donation = getDonation(DONATION_ID_1);
        when(donationRepository.save(donation)).thenReturn(donation);
        Donation donationResult = donationService.saveDonation(donation);
        assertEquals(donation, donationResult);
        verify(donationRepository).save(donation);
    }

    @Test
    void getTotalQuantityShouldReturnSumOfQuantities() {
        when(donationRepository.sumQuantity()).thenReturn(Optional.of(DONATION_QUANTITIES));
        Long quantityResult = donationService.getTotalQuantity();
        assertEquals(DONATION_QUANTITIES, quantityResult);
        verify(donationRepository).sumQuantity();
    }

    @Test
    void getTotalDonationShouldReturnTotalDonationCount() {
        when(donationRepository.count()).thenReturn(DONATION_COUNT);
        Long donationResult = donationService.getTotalDonation();
        assertEquals(DONATION_COUNT, donationResult);
        verify(donationRepository).count();
    }

    private List<Donation> getDonations() {
        Donation firstDonation = new Donation();
        firstDonation.setId(1L);
        Donation secondDonation = new Donation();
        secondDonation.setId(2L);
        return List.of(firstDonation, secondDonation);
    }

    private Donation getDonation(Long id) {
        Donation donation = new Donation();
        donation.setId(id);
        return donation;
    }

    private void assertDonationEquals(Donation expected, Donation actual) {
        assertEquals(expected.getId(), actual.getId());
    }
}
