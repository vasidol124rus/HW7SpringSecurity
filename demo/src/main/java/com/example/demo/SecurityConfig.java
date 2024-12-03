package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/private-data").hasRole("ADMIN")
                .antMatchers("/private-data").access("hasRole('ADMIN')")
                .antMatchers("/public-data").authenticated()
                .and()
                .formLogin()
                .and()
                .logout()
                .logoutSuccessUrl("/home") // Указать URL-адрес для перенаправления после выхода из системы
                .invalidateHttpSession(true) // Аннулировать HTTP-сессию
                .deleteCookies("JSESSIONID") // Удалить файл cookie JSESSIONID
                .and()
                .exceptionHandling()
                .accessDeniedPage("/home")
                .and()
                .csrf().disable(); // Отключить CSRF
        return http.build();
    }


    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user") .password("password") .roles("USER") .build();
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin") .password("password") .roles("ADMIN") .build();
        return new InMemoryUserDetailsManager(user, admin); }

}
