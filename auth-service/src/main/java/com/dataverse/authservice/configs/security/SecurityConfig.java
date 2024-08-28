package com.dataverse.authservice.configs.security;

import com.dataverse.authservice.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private UserService userService;
    private RequestFilter requestFilter;
    private BasicSecurityConfig basicSecurityConfig;
    private RateLimitingFilter rateLimitingFilter;

    @Autowired
    private void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private void setRequestFilter(RequestFilter requestFilter) {
        this.requestFilter = requestFilter;
    }

    @Autowired
    private void setBasicSecurityConfig(BasicSecurityConfig basicSecurityConfig) {
        this.basicSecurityConfig = basicSecurityConfig;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(basicSecurityConfig.passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(rateLimitingFilter, RequestFilter.class);
        return http.build();
    }
}
