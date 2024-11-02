package com.example.LFPetBackend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
//            .csrf { it.disable() }
//            .authorizeHttpRequests { auth ->
//                auth.anyRequest().permitAll()
//            }
            .csrf { it.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .requestMatchers("/user/**").hasRole("USER")
                    .anyRequest().authenticated()
            }
        return http.build()
    }

}

