package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, SuccessUserHandler successUserHandler) throws Exception {
        return http
                /*
                 * Правила читаются сверху вниз и как только я достигаю до нужного правила - остальные игнорируются.
                 */
                .csrf(AbstractHttpConfigurer::disable) // Отключается необходимость передавать cookie (или иные заговлоки) при PUT, POST, DELETE методах.
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/user").hasAnyRole("USER", "ADMIN")// Доступ для пользователей с ролями USER или ADMIN
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST).hasRole("ADMIN")  // Доступ только для пользователей с ролью ADMIN
                        .anyRequest().authenticated()  // Остальные запросы требуют аутентификации
                )
                .formLogin(form -> form// Настраиваем свою кастомную страницу для логина
                        .permitAll()
                        .successHandler(successUserHandler)
//                        .defaultSuccessUrl("/user", true)  // Перенаправление после успешного логина
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")  // URL для логаута
                        .logoutSuccessUrl("/login?logout")  // Куда перенаправлять после успешного логаута
                        .permitAll()
                )
                .build();
    }

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}