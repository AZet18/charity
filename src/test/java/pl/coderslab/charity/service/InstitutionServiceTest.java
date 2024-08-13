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

    private static final Long INSTITUTION_ID_1 = 1L;
    private static final Long INSTITUTION_ID_2 = 2L;
    private static final String INSTITUTION_NAME = "Institution";

    @Mock
    private InstitutionRepository institutionRepository;

    @InjectMocks
    private InstitutionService institutionService;

    @Test
    void getAllInstitutionsShouldReturnInstitutionList() {
        when(institutionRepository.findAll(any(Pageable.class))).thenReturn(getInstitutionPage());

        List<Institution> institutionsResult = institutionService.getAllInstitutions();
        assertEquals(2, institutionsResult.size());
        assertEquals(INSTITUTION_ID_1, institutionsResult.get(0).getId());
        assertEquals(INSTITUTION_ID_2, institutionsResult.get(1).getId());
        verify(institutionRepository).findAll(any(Pageable.class));

    }

    @Test
    void getInstitutionByIdShouldReturnInstitutionIfExists() {
        when(institutionRepository.findById(1L)).thenReturn(Optional.of(getInstitution()));

        Institution institutionResult = institutionService.getInstitutionById(INSTITUTION_ID_1);
        assertNotNull(institutionResult);
        assertEquals(INSTITUTION_NAME, institutionResult.getName());
        assertEquals(INSTITUTION_ID_1, institutionResult.getId());
        verify(institutionRepository).findById(INSTITUTION_ID_1);
    }

    @Test
    void getInstitutionByIdShouldThrowExceptionWhenNotFound() {
        when(institutionRepository.findById(INSTITUTION_ID_1)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                institutionService.getInstitutionById(INSTITUTION_ID_1));
        assertEquals("Institution not found " + INSTITUTION_ID_1, exception.getMessage());
    }
    @Test
    void deleteInstitutionShouldCallDeletedById() {
        institutionService.deleteInstitution(INSTITUTION_ID_1);
        verify(institutionRepository).deleteById(INSTITUTION_ID_1);
    }

    @Test
    void saveInstitutionShouldReturnSavedInsitution() {
        Institution institution = getInstitution();
        when(institutionRepository.save(institution)).thenReturn(institution);
        Institution institutionResult = institutionService.saveInstitution(institution);
        assertEquals(institution, institutionResult);
        verify(institutionRepository).save(institution);
    }


    private Institution getInstitution() {
        Institution institution = new Institution();
        institution.setId(INSTITUTION_ID_1);
        institution.setName(INSTITUTION_NAME);
        return institution;
    }

    private Page<Institution> getInstitutionPage() {
        Institution institution1 = new Institution();
        institution1.setId(INSTITUTION_ID_1);
        Institution institution2 = new Institution();
        institution2.setId(INSTITUTION_ID_2);
        List<Institution> institutionList = List.of(institution1, institution2);
        return new PageImpl<>(institutionList);
    }

}
