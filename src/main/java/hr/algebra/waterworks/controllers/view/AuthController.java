package hr.algebra.waterworks.controllers.view;

import hr.algebra.waterworks.publishers.LoginEventPublisher;
import hr.algebra.waterworks.services.interfaces.RegistrationService;
import hr.algebra.waterworks.shared.requests.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@AllArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private LoginEventPublisher loginEventPublisher;
    private RegistrationService registrationService;


    @GetMapping("register")
    public String getRegisterPage(Model model){
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }

    @PostMapping("register")
    public String registerNewUser(HttpServletRequest request, Model model, @ModelAttribute("registerRequest") RegisterRequest registerRequest){
        try {
            registrationService.registerNewUser(registerRequest, "USER");
            request.login(registerRequest.getUsername(), registerRequest.getPassword());
            loginEventPublisher.publishSuccessfulLogin(registerRequest.getUsername());
        } catch (Exception e){
            System.out.println(e.getMessage());
            return "register";
        }
        return "redirect:/";
    }

    @GetMapping("logout")
    public String cleanSession(SessionStatus sessionStatus, HttpSession session){
        session.invalidate();
        sessionStatus.setComplete();
        return "redirect:/";
    }

}
