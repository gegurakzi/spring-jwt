package com.cos.springjwt.config;

import com.cos.springjwt.config.jwt.JWTAuthenticationFilter;
import com.cos.springjwt.filter.MyFilter1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;

    @Bean
    public BCryptPasswordEncoder setBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    };


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.addFilterBefore(new MyFilter1(), SecurityContextPersistenceFilter.class);
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .addFilter(corsFilter)  //Credentialed Request까지 허용해줌
                    .formLogin().disable()
                    .httpBasic().disable()
                    .addFilter(new JWTAuthenticationFilter(authenticationManager()))

                .authorizeRequests()
                    .antMatchers("/api/v1/user/**")
                        .hasAnyRole("USER", "MANAGER", "ADMIN")
                    .antMatchers("api/v1/manager/**")
                        .hasAnyRole("MANAGER", "ADMIN")
                    .antMatchers("api/v1/admin/**")
                        .hasRole("ADMIN")
                    .anyRequest()
                        .permitAll();
    }
}
