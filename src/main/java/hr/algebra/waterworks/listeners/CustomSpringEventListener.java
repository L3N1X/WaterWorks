package hr.algebra.waterworks.listeners;

import hr.algebra.waterworks.events.CustomSpringEvent;
import hr.algebra.waterworks.publishers.CustomSpringEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CustomSpringEventListener implements ApplicationListener<CustomSpringEvent> {
    @Override
    public void onApplicationEvent(CustomSpringEvent event) {
        System.out.println("Recieved custom event - " + event.getMessage());
    }
}
