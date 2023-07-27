package hr.algebra.waterworks.controllers.view;

import hr.algebra.waterworks.publishers.LoginEventPublisher;
import hr.algebra.waterworks.services.interfaces.AuthService;
import hr.algebra.waterworks.shared.dtos.UserDto;
import hr.algebra.waterworks.shared.requests.ItemFilterRequest;
import hr.algebra.waterworks.shared.requests.LoginRequest;
import hr.algebra.waterworks.shared.requests.RegisterRequest;
import hr.algebra.waterworks.utils.UserAuthenticationUtil;
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


    @GetMapping("register")
    public String getRegisterPage(Model model){
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }

    @PostMapping("register")
    public String registerNewUser(Model model, @ModelAttribute("registerRequest") RegisterRequest registerRequest){
        if(true) {
            UserAuthenticationUtil.authenticateUserAndSetSession(registerRequest.getUsername());
            return "redirect:/";
        }
        return "register";
    }

    @GetMapping("logout")
    public String cleanSession(SessionStatus sessionStatus, HttpSession session){
        session.invalidate();
        sessionStatus.setComplete();
        return "redirect:/";
    }

}
