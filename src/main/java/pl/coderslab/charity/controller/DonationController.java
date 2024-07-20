package pl.coderslab.charity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.charity.entity.Category;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.service.CategoryService;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.InstitutionService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DonationController {

    private final InstitutionService institutionService;
    private final CategoryService categoryService;
    private final DonationService donationService;

    @GetMapping("/form")
    public String showDonationForm(Model model) {
        model.addAttribute("donation", new Donation());
        return "donationForm";
    }

    @PostMapping("/form")
    public String submitForm(@Valid Donation donation, BindingResult result) {
        if (result.hasErrors()) {
            return "donationForm";
        }
        donationService.saveDonation(donation);
        return "redirect:/formConfirmation";
    }

    @GetMapping("formConfirmation")
    public String formConfirmation() {
        return "formConfirmation";
    }

    @ModelAttribute("institutions")
    private List<Institution> institutionsForFormModel() {
        return institutionService.getAllInstitutions();
    }

    @ModelAttribute("categories")
    private List<Category> categoriesForFormModel() {
        return categoryService.getAllCategories();
    }


}
