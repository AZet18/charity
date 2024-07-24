package pl.coderslab.charity.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.repository.InstitutionRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InstitutionServiceTest {

    @Mock
    private InstitutionRepository institutionRepository;

    @InjectMocks
    private InstitutionService institutionService;

    @Test
    void getAllInstitutionsShouldReturnInstitutionList() {
        Institution institution1 = new Institution();
        institution1.setId(1L);
        Institution institution2 = new Institution();
        institution2.setId(2L);
        List<Institution> institutionList = List.of(institution1, institution2);
        Page<Institution> institutionPage = new PageImpl<>(institutionList);
        when(institutionRepository.findAll(any(Pageable.class))).thenReturn(institutionPage);

        List<Institution> institutionsResult = institutionService.getAllInstitutions();
        assertEquals(2, institutionsResult.size());
        assertEquals(1L, institutionsResult.get(0).getId());
        assertEquals(2L, institutionsResult.get(1).getId());
        verify(institutionRepository).findAll(any(Pageable.class));

    }

    @Test
    void getInstitutionByIdShouldReturnInstitutionIfExist() {
        Institution institution = new Institution();
        institution.setId(1L);
        institution.setName("Institution");
        when(institutionRepository.findById(1L)).thenReturn(Optional.of(institution));
        Institution institutionResult = institutionService.getInstitutionById(1L);
        assertNotNull(institutionResult);
        assertEquals("Institution", institutionResult.getName());
        assertEquals(1L, institutionResult.getId());
        verify(institutionRepository).findById(1L);
    }

    @Test
    void getInstitutionByIdShouldThrowExceptionWhenNotFound() {
        Long institutionID = 1L;
        when(institutionRepository.findById(institutionID)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                institutionService.getInstitutionById(institutionID));
        assertEquals("Institution not found " + institutionID, exception.getMessage());
    }
    @Test
    void deleteInstitutionShouldCallDeletedById() {
        Long institutionId = 1L;
        institutionService.deleteInstitution(institutionId);
        verify(institutionRepository).deleteById(institutionId);
    }

    @Test
    void saveInstitutionShouldReturnSavedInsitution() {
        Institution institution = new Institution();
        institution.setId(1L);
        when(institutionRepository.save(institution)).thenReturn(institution);
        Institution institutionResult = institutionService.saveInstitution(institution);
        assertEquals(institution, institutionResult);
        verify(institutionRepository).save(institution);
    }

}
