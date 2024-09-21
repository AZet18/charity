package pl.coderslab.charity.controller;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.valves.rewrite.Substitution;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.repository.DonationRepository;
import pl.coderslab.charity.repository.InstitutionRepository;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.InstitutionService;
import pl.coderslab.charity.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class HomeController {

    private final InstitutionService institutionService;
    private final DonationService donationService;
    private final UserService userService;

    private final DonationRepository donationRepository;


    @RequestMapping("/")
    public String homeAction(Model model, HttpSession session) {
        model.addAttribute("institutions", institutionService.getAllInstitutions());
        model.addAttribute("sumOfQuantity", donationService.getTotalQuantity());
        model.addAttribute("allDonations", donationService.getTotalDonation());

        userService.getLoggedUser(session).ifPresent(user -> model.addAttribute("user", user));

        return "index";
    }



}
