package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import static com.example.demo.security.UserRole.*;

import java.util.concurrent.TimeUnit;

import com.example.demo.auth.ApplicationUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder,ApplicationUserService applicationUserService){
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/","index","/css/*","/js/*").permitAll()
            .antMatchers("/api/**").hasRole(STUDENT.name())
            .antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(UserPermission.COURSE_WRITE.getPermission())
            .antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(UserPermission.COURSE_WRITE.getPermission())
            .antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(UserPermission.COURSE_WRITE.getPermission())
            .antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ADMIN.name(),ADMINTRAINEE.name())
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/course",true)
                .passwordParameter("password")
                .usernameParameter("username")
            .and()
            .rememberMe()
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                .key("secured")
                .rememberMeParameter("remember-me")
            .and()
            .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID","remember-me")
                .logoutSuccessUrl("/login");
    }  

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }
}
