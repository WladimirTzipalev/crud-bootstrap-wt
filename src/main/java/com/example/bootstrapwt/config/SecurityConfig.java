package com.example.bootstrapwt.config;

import com.example.bootstrapwt.config.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private LoginSuccessHandler handler;

    @Autowired
    public SecurityConfig(LoginSuccessHandler handler) {
        this.handler = handler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()

                .loginPage("/login") // указываем страницу с формой логина

                .successHandler(handler) //указываем логику обработки при логине

                .loginProcessingUrl("/login") // указываем action с формы логина

                .usernameParameter("j_username") // Указываем параметры логина и пароля с формы логина
                .passwordParameter("j_password")

                .permitAll(); // даем доступ к форме логина всем

        http.logout()

                .permitAll() // разрешаем делать логаут всем

                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // указываем URL логаута

                .logoutSuccessUrl("/login?logout")  // указываем URL при удачном логауте

                .and().csrf().disable(); //выключаем кроссдоменную секьюрность (на этапе обучения неважна)

        http

                .authorizeRequests() // делаем страницу регистрации недоступной для авторизированных пользователей

                .antMatchers("/login").anonymous() //страницы аутентификаци доступна всем

                .antMatchers("/user").hasAuthority("USER") // защищенные URL
                .antMatchers("/**").access("hasAuthority('ADMIN')").anyRequest().authenticated();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
