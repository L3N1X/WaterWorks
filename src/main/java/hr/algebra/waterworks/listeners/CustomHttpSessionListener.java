package hr.algebra.waterworks.listeners;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class CustomHttpSessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {

        String ipAddress = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes())
                .getRequest().getRemoteAddr();

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    }
}
