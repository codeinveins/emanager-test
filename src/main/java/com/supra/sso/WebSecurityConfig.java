package com.supra.sso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.supra.sso.utiities.CustomAuthenticationSuccessHandler;
import com.supra.sso.utiities.CustomLogoutHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
    	return new CustomAuthenticationSuccessHandler();
    }
    
    @Bean
    public CustomLogoutHandler customLogoutHandler() {
    	return new CustomLogoutHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
                http.authorizeRequests()
                    .antMatchers("/resources/**", "/registration").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    
                    .formLogin()
                    .loginPage("/login?module=")
                    .successHandler(customAuthenticationSuccessHandler())
                    .permitAll()
                    
                    .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .addLogoutHandler(customLogoutHandler())
                    .logoutSuccessUrl("/login")
                    .permitAll()
                    
                    .and()
                    .csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}