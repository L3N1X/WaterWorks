package hr.algebra.waterworks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/").permitAll()
                        .requestMatchers("manage/logins").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .successHandler(successHandler())
                )
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
        handler.setDefaultTargetUrl("/");
        handler.setUseReferer(false);
        return handler;
    }

    @Bean
    public UserDetailsService userDetailsService(final AuthenticationManagerBuilder auth) throws Exception {

        /*UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();
        return new InMemoryUserDetailsManager(user);*/
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        final User.UserBuilder userBuilder = User.builder();
        UserDetails user = userBuilder
                .username("user")
                .password("$2a$12$YL3AceI0oFWO0N.eHHagweNhOsmKOm9FFuiC0BajDWjfOp3gJrztC")
                .roles("USER")
                .build();
        UserDetails admin = userBuilder
                .username("admin")
                .password("$2a$12$YL3AceI0oFWO0N.eHHagweNhOsmKOm9FFuiC0BajDWjfOp3gJrztC")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
