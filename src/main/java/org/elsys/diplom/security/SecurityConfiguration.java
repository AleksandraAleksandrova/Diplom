package org.elsys.diplom.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> {
                    try {
                        auth.requestMatchers(
                                        new AntPathRequestMatcher("/h2-console/**"),
                                        new AntPathRequestMatcher("/login"),
                                        new AntPathRequestMatcher("/register"),
                                        new AntPathRequestMatcher("/css/**"),
                                        new AntPathRequestMatcher("/js/**"),
                                        new AntPathRequestMatcher("/images/**"),
                                        new AntPathRequestMatcher("/confirm-account"),
                                        new AntPathRequestMatcher("/welcome")).permitAll()
                                .anyRequest().authenticated().and()
                                .headers().frameOptions().disable();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .formLogin(form -> {
                    form.loginPage("/login");
                    form.defaultSuccessUrl("/home", true);
                    form.failureForwardUrl("/login");
                    form.failureUrl("/login?error=true");
                })
                .logout(logout -> {
                    logout.logoutUrl("/logout");
                    logout.logoutSuccessUrl("/welcome");
                    logout.clearAuthentication(true);
                    logout.invalidateHttpSession(true);
                    logout.deleteCookies("JSESSIONID");
                })
                .build();
    }
}