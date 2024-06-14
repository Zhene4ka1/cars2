package com.example.exam.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurity {

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeRequests()
                .requestMatchers("/js/**","/css/**","/static/**")
                .permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/statement/admin/**").hasAnyAuthority("Администратор")
                .anyRequest().permitAll()
                .and()
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/profile", true)
                                .permitAll()
                                .and()
                )
                .rememberMe()
                .key("my-super-secret-key")
                .tokenValiditySeconds(604800)
                .and()
                .logout(
                        logout -> logout
                                .logoutRequestMatcher(new RequestHeaderRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }
}
