package com.config;


import com.security.jwt.JwtAuthenticationEntryPoint;
import com.security.jwt.JwtRequestFilter;
import com.user.application.UserApplicationService;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static com.user.domain.UserRole.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@EnableWebMvc
class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final UserApplicationService userApplicationService;
    private final JwtRequestFilter jwtRequestFilter;


    @Autowired
    SpringSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, UserApplicationService userApplicationService, JwtRequestFilter jwtRequestFilter){

        Assert.notNull(jwtAuthenticationEntryPoint, "jwtAuthenticationEntryPoint must be not null");
        Assert.notNull(userApplicationService, "userService must be not null");
        Assert.notNull(jwtRequestFilter, "jwtRequestFilter must be not null");

        this.userApplicationService = userApplicationService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtRequestFilter = jwtRequestFilter;
    }


    @Bean
    PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userApplicationService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new
                UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .cors()
                .and()
                .authorizeRequests().antMatchers("/auth").permitAll()
                .antMatchers("/api/admin/**").hasRole(ADMIN.name())
                .antMatchers("/api/student/**").hasRole(STUDENT.name())
                .antMatchers("/api/tutor/**").hasAnyRole(TUTOR.name(), ADMIN.name(), ADM_WORKER.name())
                .antMatchers("/api/adm/**").hasRole(ADM_WORKER.name())
                .antMatchers("/api/user/**").hasAnyRole(ADM_WORKER.name(), ADMIN.name(), STUDENT.name(), TUTOR.name())
                .antMatchers(GET, "/api/faculty/**").hasAnyRole(ADM_WORKER.name(), ADMIN.name(), STUDENT.name(), TUTOR.name())
                .antMatchers(POST, "/api/faculty/**").hasRole(ADMIN.name())

                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}