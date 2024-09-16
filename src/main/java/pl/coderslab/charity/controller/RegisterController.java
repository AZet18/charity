package pl.coderslab.charity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.service.UserService;

import javax.servlet.http.HttpSession;


@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model, HttpSession session){
        if (userService.getLoggedUser(session).isPresent()) {
            return "redirect:/";
        }
        model.addAttribute("user", new User());
        return "registerForm";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "registerForm";
        }
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            result.rejectValue("email","error.email.exists");
            return "registerForm";
        }
        try {
            userService.saveUser(user);
        } catch (IllegalArgumentException e) {
            result.rejectValue("password", "error.password.exists");
            result.rejectValue("passwordRepeat", "error.passwordRepeat.exists");
            return "registerForm";
        }
        return "redirect:/login";
    }
}
