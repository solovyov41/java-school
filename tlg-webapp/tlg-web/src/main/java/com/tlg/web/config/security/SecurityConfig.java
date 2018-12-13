package com.tlg.web.config.security;

import com.tlg.core.entity.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@ComponentScan({"com.tlg.core.service", "com.tlg.web.config"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private UserDetailsService userDetailsService;

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder);
        return authProvider;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                // Spring Security should completely ignore URLs starting with /assets/
                .antMatchers("/assets/**").and().debug(true);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/registration").permitAll()
                .antMatchers("/account/users/**", "/account/users").hasAuthority(Role.ADMIN.name())
                .antMatchers("/manager/**").hasAuthority(Role.DISPATCHER.name())
                .antMatchers("/account/**", "/account").authenticated()
                .antMatchers("/rest/**").permitAll()
                .and().formLogin().loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/account/dashboard").failureUrl("/login?error")
                .usernameParameter("email").passwordParameter("password")
                .and().rememberMe()
                .and().csrf()
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout").invalidateHttpSession(true);
        http.sessionManagement().invalidSessionUrl("/login");
        http.exceptionHandling().accessDeniedPage("/error");
    }

}
