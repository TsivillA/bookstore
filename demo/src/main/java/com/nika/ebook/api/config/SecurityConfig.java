package com.nika.ebook.api.config;

import com.nika.ebook.api.filter.AuthTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.nika.ebook.api.constants.RoleConstants.ROLE_ADMIN;
import static com.nika.ebook.api.constants.RoleConstants.ROLE_USER;
import static com.nika.ebook.api.constants.RouteConstants.*;
import static com.nika.ebook.api.constants.SwaggerConstant.PUBLIC_URLS;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final AuthEntryPointJwt unauthorizedHandler;
    private final AuthTokenFilter authTokenFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                        .antMatchers(ALL_AUTH_ROUTES).permitAll()
                        .antMatchers(GET_ALL_BOOKS).permitAll()
                        .antMatchers(ALL_GET_BOOKS_WITH_CATEGORY_ROUTES).permitAll()
                        .antMatchers(ALL_GET_BOOK_ROUTES).permitAll()
                        .antMatchers(PUBLIC_URLS).permitAll()
                        .antMatchers(USERS_API).hasAnyAuthority(ROLE_ADMIN)
                        .antMatchers(ALL_USERS_WITH_BOOKS_ROUTES).hasAnyAuthority(ROLE_USER, ROLE_ADMIN)
                        .antMatchers(BOOKS_API).hasAnyAuthority(ROLE_ADMIN)
                        .anyRequest().authenticated();
        http.addFilterBefore(authTokenFilter,UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
