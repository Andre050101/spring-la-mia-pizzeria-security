package org.lessons.java.spring_la_mia_pizzeria_relazioni.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    @SuppressWarnings("removal")
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/pizzas/create", "/pizzas/edit/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/pizzas/**").hasAuthority("ADMIN")
                .requestMatchers("/discount", "/discount/**").hasAuthority("ADMIN")
                .requestMatchers("/pizzas", "/pizzas/**").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers("/**").permitAll()
                .and().formLogin()
                .and().logout()
                .and().exceptionHandling();
        return http.build();
    }

    @SuppressWarnings("deprecation")
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        /* Questo provider userà x come servizio di recupero utenti via username */
        authProvider.setUserDetailsService(databaseUserDetailService());

        /* Questo provider userà y come password encoder */
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    DatabaseUserDetailService databaseUserDetailService() {
        return new DatabaseUserDetailService();
    }
}
