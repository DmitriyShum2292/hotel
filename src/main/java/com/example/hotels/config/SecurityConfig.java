package com.example.hotels.config;

import com.example.hotels.hmac.HMACAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.ConcurrentSessionFilter;

import javax.sql.DataSource;

@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select login, password, active from sys_user " +
                                "where login=?")
                .authoritiesByUsernameQuery(
                        "select login, authority from sys_user " +
                                "where login=?")
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**");
        http.authorizeRequests()
                .antMatchers("/cabinet","/cabinet/order/**").hasAnyRole("USER", "ADMIN")
                .antMatchers().hasRole("USER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/login","/hotels","/hotel","/registration","/api/v1/hotels/test").permitAll()
                .and()
                    .formLogin()
                .and()
                    .logout()
                    .permitAll();

    }

}

