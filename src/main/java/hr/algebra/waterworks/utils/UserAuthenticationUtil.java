package hr.algebra.waterworks.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collections;

public class UserAuthenticationUtil {
    private UserAuthenticationUtil(){}

    public static void authenticateUserAndSetSession(String username) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, Collections.singletonList(authority));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
