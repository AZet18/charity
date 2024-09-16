package pl.coderslab.charity.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.coderslab.charity.entity.Category;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.service.CategoryService;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.InstitutionService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DonationController.class)
class DonationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InstitutionService institutionService;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private DonationService donationService;

    @InjectMocks
    private DonationController donationController;

    @Test
    public void testShowDonationForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/form"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("donation"))
                .andExpect(view().name("donationForm"));

    }

    @Test
    public void testShowConfirmationForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/formConfirmation"))
                .andExpect(status().isOk())
                .andExpect(view().name("formConfirmation"));

    }

    @Test
    public void testInstitutionsList() throws Exception {
        List<Institution> institutions = new ArrayList<>();
        institutions.add(new Institution());
        institutions.add(new Institution());
        when(institutionService.getAllInstitutions()).thenReturn(institutions);

        mockMvc.perform(MockMvcRequestBuilders.get("/form"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("institutions"))
                .andExpect(model().attribute("institutions", institutions));
    }

    @Test
    public void testCategoriesList() throws Exception {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category());
        categories.add(new Category());
        when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(MockMvcRequestBuilders.get("/form"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attribute("categories", categories));
    }

    @Test
    public void testSubmitFormWithErrors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/form")
                        .param("categories", "")
                        .param("institution", "")
                        .param("street", "")
                        .param("city", "")
                        .param("zipCode", "")
                        .param("pickUpDate", "")
                        .param("pickUpTime", "")
                        .param("pickUpComment", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("donationForm"));
    }

    @Test
    public void testSubmitFormSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/form")
                        .param("categories", "1")
                        .param("institution", "1")
                        .param("quantity", "10")
                        .param("street", "Example Street")
                        .param("city", "Example City")
                        .param("zipCode", "00-000")
                        .param("phoneNumber", "123456789")
                        .param("pickUpDate", "2024-08-10")
                        .param("pickUpTime", "10:00")
                        .param("pickUpComment", "No comment"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(redirectedUrl("/formConfirmation"));
    }
}