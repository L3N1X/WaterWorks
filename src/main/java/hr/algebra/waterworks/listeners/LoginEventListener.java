package hr.algebra.waterworks.listeners;

import hr.algebra.waterworks.dao.entities.Login;
import hr.algebra.waterworks.events.LoginEvent;
import hr.algebra.waterworks.services.interfaces.WaterWorksService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class LoginEventListener implements ApplicationListener<LoginEvent> {

    private WaterWorksService waterWorksService;

    @Override
    public void onApplicationEvent(LoginEvent event) {
        String username = event.getUsername();
        LocalDateTime dateTime = LocalDateTime.now();
        String ipAddress = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getRemoteAddr();
        waterWorksService.createLogin(new Login(username, ipAddress, dateTime));
        System.out.println("Received custom event, user id: " + username + " ip: " + ipAddress + " date time: " + dateTime);
    }
}
