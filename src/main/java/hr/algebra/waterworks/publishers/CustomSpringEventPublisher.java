package hr.algebra.waterworks.publishers;

import hr.algebra.waterworks.events.CustomSpringEvent;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomSpringEventPublisher {

    private ApplicationEventPublisher applicationEventPublisher;

    public void publishCustomEvent(final String message){
        System.out.println("Publishing custom event...");
        CustomSpringEvent customSpringEvent = new CustomSpringEvent(this, message);
        applicationEventPublisher.publishEvent(customSpringEvent);
    }

}
