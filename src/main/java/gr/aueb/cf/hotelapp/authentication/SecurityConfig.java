package gr.aueb.cf.hotelapp.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/login", "/register", "/public/**").permitAll()
                        .requestMatchers("/images/**", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/about", "/hotel/employees/registered", "/hotel/clients/registered").permitAll()
                        .requestMatchers("/hotel/clients/insert", "/hotel/clients/insert/**").permitAll()
                        .requestMatchers("/hotel/employees/insert").permitAll()
                        .requestMatchers("/hotel/rooms/**").permitAll()
                        .requestMatchers("/hotel/clients/**").hasAuthority("ROLE_CLIENT")
                        .requestMatchers("/hotel/employees/**").hasAuthority("ROLE_EMPLOYEE")
                        .requestMatchers("/hotel/reservations/**").authenticated()
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .httpBasic(Customizer.withDefaults())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

}
