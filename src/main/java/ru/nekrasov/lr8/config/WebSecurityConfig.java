package ru.nekrasov.lr8.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    @Bean
    public static PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/register/**").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/set-new-roles").hasAnyRole("ADMIN")
                .antMatchers("/users").hasAnyRole("ADMIN", "MANAGER", "ADMINISTRATOR")
                .antMatchers("/daily").hasAnyRole("ADMIN", "MANAGER", "ADMINISTRATOR")
                .antMatchers("/readonly-resource").hasRole("READONLY")
                .and()

                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .successHandler((request, response, authentication) -> {
                                    if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN") || r.getAuthority().equals("ROLE_MANAGER") || r.getAuthority().equals("ROLE_ADMINISTRATOR"))) {
                                        response.sendRedirect("/users");
                                    } else if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_READONLY"))) {
                                        response.sendRedirect("/readonly-resource");
                                    } else {
                                        response.sendRedirect("/index");
                                    }
                                })
                                .permitAll()
                )
                .logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }

}
