package hr.algebra.waterworks.controllers.view;

import hr.algebra.waterworks.publishers.LoginEventPublisher;
import hr.algebra.waterworks.services.implementations.AuthServiceJdbc;
import hr.algebra.waterworks.services.interfaces.AuthService;
import hr.algebra.waterworks.services.interfaces.WaterWorksService;
import hr.algebra.waterworks.shared.dtos.UserDto;
import hr.algebra.waterworks.shared.requests.ItemFilterRequest;
import hr.algebra.waterworks.shared.requests.LoginRequest;
import hr.algebra.waterworks.shared.sessionmodels.Cart;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private LoginEventPublisher loginEventPublisher;
    private AuthService authService;

    @GetMapping("login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @PostMapping("login")
    public String login(Model model, @ModelAttribute("loginRequest") LoginRequest loginRequest) {
        UserDto user = authService.tryAuthenticate(loginRequest);
        if(user == null) {
            model.addAttribute("errorMessage", "Korisničko ime ili lozinka nisu ispravni");
            model.addAttribute("loginRequest", new LoginRequest(loginRequest.getEmail(), null));
            return "login";
        }
        loginEventPublisher.publishSuccessfulLogin(user.id());
        return "redirect:/";
    }

    @GetMapping("register")
    public String getRegisterPage(){
        return "register";
    }

    @GetMapping("logout")
    public String cleanSession(SessionStatus sessionStatus){
        sessionStatus.setComplete();
        return "redirect:/";
    }

}
