package hr.algebra.waterworks.controllers.view;

import hr.algebra.waterworks.shared.sessionmodels.Cart;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@AllArgsConstructor
@RequestMapping("auth")
@SessionAttributes("cart")
public class AuthController {

    @GetMapping("login")
    public String getLoginPage() {
        return "login";
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
