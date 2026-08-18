package com.eazybytes.springsection7.config;

import com.eazybytes.springsection7.exceptionhandling.CustomAccessDeniedHandler;
import com.eazybytes.springsection7.exceptionhandling.CustomBasicAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("prod")
public class ProjectSecurityProdConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(smc -> smc.invalidSessionUrl("/invalidSession").maximumSessions(1).maxSessionsPreventsLogin(true))
                .redirectToHttps((https) -> https.requestMatchers(AnyRequestMatcher.INSTANCE))    //Only Https traffic is accepted not Http
                .csrf(csrfConfig -> csrfConfig.disable())
                .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
                .requestMatchers("/myNotices", "/myContact", "/error","/register","/invalidSession").permitAll());
          http.formLogin(withDefaults());
         http.httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));

        return http.build();
    }

    //Configuring users using InMemoryUserDetailsManager
   /* @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user = User.withUsername("user").password("{noop}EazyBytes@12345").authorities("read").build();
        UserDetails admin = User.withUsername("admin")
                *//*.password("{bcrypt}$2a$12$/g3H1CxFtWmNUtZyWtW65.XzjSHyvEYwqOZfmR6CiGOUt9qao4OrS")*//*
                .password("{bcrypt}$2a$12$AgJBrOjZCHFZk5guZx5z3urXcMmkDKRSpljg60CJW64JNVXxst28y")
                .authorities("admin").build();
        return new InMemoryUserDetailsManager(user, admin);
    }*/

    //Configuring PasswordEncoder using PasswordEncoderFactories
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    //Example for CompromisedPasswordChecker
    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker(){
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }


    //using JdbcUserDetailManager to perform authentication
   /* @Bean
    public UserDetailsService userDetailsService(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }*/
}

