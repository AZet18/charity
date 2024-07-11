package pl.coderslab.charity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.service.DonationService;

@Controller
@RequiredArgsConstructor
public class DonationController {

    private final DonationService donationService;

    @GetMapping("/form")
    public String showForm(Model model){
        model.addAttribute("donation", new Donation());
        return "/form";
    }


}
