package hr.algebra.waterworks.events;

import org.springframework.context.ApplicationEvent;

public class LoginEvent extends ApplicationEvent {

    private final int userId;
    public LoginEvent(Object source, int userId) {
        super(source);
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
}
