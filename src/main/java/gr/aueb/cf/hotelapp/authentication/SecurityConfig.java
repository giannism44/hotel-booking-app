package gr.aueb.cf.hotelapp.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationSuccessHandler successHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/login", "/register", "/about", "/spa", "/restaurant").permitAll()
                        .requestMatchers("/images/**", "/css/**", "/js/**", "/public/**").permitAll()

                        //Clients
                        .requestMatchers("/hotel/clients/insert", "/hotel/clients/insert/**", "/hotel/clients/registered").permitAll()
                        .requestMatchers("/hotel/clients/profile").hasAuthority("ROLE_CLIENT")
                        .requestMatchers("/hotel/clients/**", "/management").hasAuthority("ROLE_EMPLOYEE")

                        //Employees
                        .requestMatchers("/hotel/employees/insert", "/hotel/employees/registered").permitAll()
                        .requestMatchers("/hotel/employees/**").hasAuthority("ROLE_EMPLOYEE")

                        //Reservations
                        .requestMatchers("/hotel/reservations/success").permitAll()
                        .requestMatchers("/hotel/reservations/insert", "/hotel/reservations/available-rooms", "/hotel/reservations/{id}").hasAnyAuthority("ROLE_CLIENT", "ROLE_EMPLOYEE")
                        .requestMatchers("/hotel/reservations/management/**").hasAuthority("ROLE_EMPLOYEE")

                        //Rooms
                        .requestMatchers("/hotel/rooms").permitAll()
                        .requestMatchers("/hotel/rooms/management/**", "/hotel/rooms/update/**").hasAuthority("ROLE_EMPLOYEE")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(successHandler)
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
