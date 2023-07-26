package hr.algebra.waterworks.controllers.view;

import hr.algebra.waterworks.publishers.LoginEventPublisher;
import hr.algebra.waterworks.services.interfaces.AuthService;
import hr.algebra.waterworks.shared.dtos.UserDto;
import hr.algebra.waterworks.shared.requests.LoginRequest;
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
    public String getRegisterPage(){
        return "register";
    }

    @GetMapping("logout")
    public String cleanSession(SessionStatus sessionStatus){
        sessionStatus.setComplete();
        return "redirect:/";
    }

}
