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
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @RequestMapping("/login")
    public String showLogin(Model model, HttpSession session){
        if (userService.getLoggedUser(session).isPresent()) {
            return "redirect:/"; //juz zalogowany TODO
        }
        model.addAttribute("user", new User());
        return "loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, BindingResult result, HttpSession session, Model model) {
        Optional<User> existingUser = userService.findByEmail(user.getEmail());

        // Sprawdzenie, czy użytkownik istnieje
        if (!existingUser.isPresent()) {
            result.rejectValue("email", "error.user");
            return "loginForm"; // Zwracamy widok logowania z błędem
        }

        // Weryfikacja hasła
        boolean passwordCorrect = userService.login(session, user.getEmail(), user.getPassword());
        if (!passwordCorrect) {
            result.rejectValue("password", "error.password", "Nieprawidłowy email lub hasło");
            return "loginForm";  // Powrót do widoku logowania z błędem
        }

        // Jeśli poprawne logowanie, przekierowanie na stronę główną
        return "redirect:/index";
    }



    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        userService.logout(session);
        return "redirect:/";
    }

}
