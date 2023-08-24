package com.egg.news;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurity {

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf((csrf) -> csrf.disable())
                                .authorizeHttpRequests((requests) -> requests
                                                .requestMatchers("/admin/**").hasRole("PERIODISTA")
                                                .requestMatchers("/admin/**").hasRole("ADMIN")

                                                .requestMatchers("/css/*", "/js/*", "/img/*", "/**").permitAll())
                                .formLogin((form) -> form
                                                .loginPage("/login")
                                                .loginProcessingUrl("/logincheck")
                                                .usernameParameter("email")
                                                .passwordParameter("password")
                                                .defaultSuccessUrl("/inicio")
                                                .permitAll())
                                .logout((logout) -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/")
                                                .permitAll());

                return http.build();
        }

}
