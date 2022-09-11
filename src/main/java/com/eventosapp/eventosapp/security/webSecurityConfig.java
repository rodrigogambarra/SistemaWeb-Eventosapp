package com.eventosapp.eventosapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class webSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ImplementsUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeRequests()

                                        .antMatchers(HttpMethod.GET,"/").permitAll()
                                        .antMatchers("/eventos").authenticated()
                                        .antMatchers(HttpMethod.POST,"/*").hasRole("ADMIN")
                                        .antMatchers(HttpMethod.GET,"/deletarConvidado").hasRole("ADMIN")
                                        .antMatchers(HttpMethod.GET,"/deletarEvento").hasRole("ADMIN")
                                        .antMatchers(HttpMethod.GET,"/cadastrarEvento").hasRole("ADMIN")
                                        .antMatchers(HttpMethod.POST,"/cadastrarEvento").hasRole("ADMIN")

                                        .anyRequest().authenticated()
                                        .and().formLogin().permitAll()
                                        .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web){
        web.ignoring().mvcMatchers("/materialize/**","/style/**");

    }
}
