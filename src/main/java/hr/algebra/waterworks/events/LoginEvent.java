package hr.algebra.waterworks.events;

import org.springframework.context.ApplicationEvent;

public class LoginEvent extends ApplicationEvent {

    private final String username;
    public LoginEvent(Object source, String username) {
        super(source);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
