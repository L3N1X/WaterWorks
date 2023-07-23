package hr.algebra.waterworks.events;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class CustomSpringEvent extends ApplicationEvent {

    private final String message;
    public CustomSpringEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
