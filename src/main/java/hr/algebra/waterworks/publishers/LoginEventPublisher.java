package hr.algebra.waterworks.publishers;

import hr.algebra.waterworks.events.LoginEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LoginEventPublisher {

    private ApplicationEventPublisher applicationEventPublisher;

    public void publishSuccessfulLogin(final String username){
        System.out.println("User with id{" + username + "} logged in successfully");
        LoginEvent loginEvent = new LoginEvent(this, username);
        applicationEventPublisher.publishEvent(loginEvent);
    }

}
