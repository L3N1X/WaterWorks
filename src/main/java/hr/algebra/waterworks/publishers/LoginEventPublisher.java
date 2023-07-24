package hr.algebra.waterworks.publishers;

import hr.algebra.waterworks.events.LoginEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LoginEventPublisher {

    private ApplicationEventPublisher applicationEventPublisher;

    public void publishSuccessfulLogin(final int userId){
        System.out.println("User with id{" + userId + "} logged in successfully");
        LoginEvent loginEvent = new LoginEvent(this, userId);
        applicationEventPublisher.publishEvent(loginEvent);
    }

}
