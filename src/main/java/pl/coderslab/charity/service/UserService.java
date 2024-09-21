package pl.coderslab.charity.service;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {


    private static final String LOGGED_USER_EMAIL_ATTRIBUTE = "loggedUserEmail";
    private static final String LOGGED_USER_ID_ATTRIBUTE = "loggedUserId";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void saveUser(User user) {
        if (!user.getPassword().equals(user.getPasswordRepeat())) {
            throw new IllegalArgumentException("Passwords not the same!");
        }
        //TODO
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public boolean login(HttpSession session, String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Weryfikacja hasła przy użyciu PasswordEncoder
            if (passwordEncoder.matches(password, user.getPassword())) {
                session.setAttribute(LOGGED_USER_EMAIL_ATTRIBUTE, user.getEmail());
                session.setAttribute(LOGGED_USER_ID_ATTRIBUTE, user.getId());
                return true;
            }
        }
        return false;
    }

    public Optional<LoggedUser> getLoggedUser(HttpSession session) {
        Long id = (Long) session.getAttribute(LOGGED_USER_ID_ATTRIBUTE);
        String email = (String) session.getAttribute(LOGGED_USER_EMAIL_ATTRIBUTE);
        if (id != null && email != null) {
            return Optional.of(LoggedUser.of(id, email));
        }
        return Optional.empty();
    }

    public void logout(HttpSession session) {
        session.removeAttribute(LOGGED_USER_EMAIL_ATTRIBUTE);
        session.removeAttribute(LOGGED_USER_ID_ATTRIBUTE);
        session.invalidate();
    }


    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Value(staticConstructor = "of")
    public static class LoggedUser {
        private Long id;
        private String email;
    }
}
