package com.eventosapp.eventosapp.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class webSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeRequests()
                                        .antMatchers("/deletarEvento").hasRole("ADMIN")
                                        .antMatchers("/deletarConvidado").hasRole("ADMIN")
                                        .antMatchers("/cadastrarEvento").hasRole("ADMIN")
                                        //.antMatchers("/eventos").hasRole("USER")
                                        .antMatchers(HttpMethod.GET,"/**").permitAll()
                                        .anyRequest().authenticated()
                                        .and().formLogin().permitAll()
                                        .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
                //.anyRequest()
                //.authenticated()
                //.and()
                //.formLogin()
                //.and()
                //.httpBasic();

        //EXEMPLO
        //http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
        //                                .antMatchers("/**").hasRole("USER").and().formLogin();
        //
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth
                // enable in memory based authentication with a user named
                // "user" and "admin"
                .inMemoryAuthentication().withUser("michelli").password("123").roles("ADMIN")
                .and()
                .withUser("rodrigo").password("123").roles("USER", "ADMIN");
    }

    /*@Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring().mvcMatchers("/materialize/**","/style/**");

    }*/


}
