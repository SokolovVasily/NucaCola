package com.vasily_sokolov.nucacola.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(
                                        new AntPathRequestMatcher("/swagger-ui/index.html"),

                                        new AntPathRequestMatcher("/product/create"),
                                        new AntPathRequestMatcher("/product/put"),
                                        new AntPathRequestMatcher("/product/delete/**"),

                                        new AntPathRequestMatcher("/rawMaterial/create"),
                                        new AntPathRequestMatcher("/rawMaterial/put"),
                                        new AntPathRequestMatcher("/rawMaterial/delete/**"),

                                        new AntPathRequestMatcher("/sale/create"),
                                        new AntPathRequestMatcher("/sale/put"),
                                        new AntPathRequestMatcher("/sale/delete/**")
                                ).hasRole("ADMIN")
                                .anyRequest()
                                .permitAll())
                .formLogin(Customizer.withDefaults())
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .build();
    }

    @Bean
    public UserDetailsService createUserInMemory() {
        UserDetails admin = User.builder()
                .passwordEncoder(new BCryptPasswordEncoder()::encode)
                .username("Administrator")
                .password("1979")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }
}
