package io.recheck.jobsapp.bookingvet.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username,password,enabled "
                        + "from users "
                        + "where username = ?")
                .authoritiesByUsernameQuery("select username,authority "
                        + "from authorities "
                        + "where username = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.DELETE, "/booking*").permitAll()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.GET,"/booking*", "/booking/**").hasRole("USER")
            .antMatchers(HttpMethod.POST,"/booking*", "/booking/**").hasRole("USER")
            .antMatchers(HttpMethod.PUT,"/booking*", "/booking/**").hasRole("USER")
            .antMatchers(HttpMethod.POST,"/login").authenticated()
            .and()
            .httpBasic()
            .authenticationEntryPoint(authenticationEntryPoint);

        http.addFilterAfter(new CustomFilter(), BasicAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
