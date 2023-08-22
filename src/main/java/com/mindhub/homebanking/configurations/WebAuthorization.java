package com.mindhub.homebanking.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity
public class WebAuthorization extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()

                 .antMatchers("/api/**").permitAll()
                 .antMatchers("/manager.html").hasAuthority("ADMIN")
                 .antMatchers("/h2-console/**").hasAuthority("ADMIN")
                 .antMatchers("/accounts.html","/account.html").hasAuthority("CLIENT")
                 .antMatchers("/web/**","/script/**","/imagenes/**","/assets/**").permitAll()
                 .antMatchers(HttpMethod.POST, "/api/clients/current").permitAll()
                 .antMatchers(HttpMethod.POST,"/api/login").permitAll()
                 .antMatchers(HttpMethod.POST,"/api/logout").permitAll()
                 .antMatchers(HttpMethod.POST,"/api/clients").permitAll()
                 .antMatchers("/index.html").permitAll();



        http.formLogin()

                .usernameParameter("email")

                .passwordParameter("password")

                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout");


        http.csrf().disable();

        //disabling frameOptions so h2-console can be accessed

        http.headers().frameOptions().disable();

        // if user is not authenticated, just send an authentication failure response

        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication

        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response

        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response

        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

    }

    private void clearAuthenticationAttributes(HttpServletRequest request){

            HttpSession session = request.getSession(false);

            if (session != null) {

                session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

            }

    }
}





