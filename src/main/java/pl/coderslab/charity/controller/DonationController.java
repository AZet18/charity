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

@Controller
@RequiredArgsConstructor
public class DonationController {

    private final InstitutionService institutionService;
    private final CategoryService categoryService;
    private final DonationService donationService;

    @GetMapping("/form")
    public String showDonationForm(Model model) {
        model.addAttribute("donation", new Donation());
        model.addAttribute("institutions", institutionService.getAllInstitutions());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "donationForm";
    }

    @PostMapping("/form")
    public String submitForm(@Valid @ModelAttribute("donation") Donation donation, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("institutions", institutionService.getAllInstitutions());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "donationForm";
        }
        donationService.saveDonation(donation);
        return "redirect:/formConfirmation";
    }




}
