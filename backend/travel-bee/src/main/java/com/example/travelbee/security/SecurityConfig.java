package com.example.travelbee.security;

import com.example.travelbee.security.jwt.JwtRequestFilter;
import com.example.travelbee.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// WebSecurityConfig se extiende de WebSecurityConfigurerAdapter para personalizar la seguridad.
// Estos beans se configuran y son inicializados acá:
// 1. JwtTokenFilter
// 2. PasswordEncoder
//Aldemás, en el método configure(HttpSecurity http) se definen patrones de los endpoints de la API protegidos y no protegidos.

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //utilizamos nuestro propio servicio de usuarios y un encriptador de spring
        auth.userDetailsService(userDetailsServiceImpl);

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //se configura la seguridad(recursos, filtros, etc)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT).hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/signup").permitAll()
                .antMatchers(HttpMethod.POST, "/signin").permitAll()
                .antMatchers(HttpMethod.POST, "/features").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/policies").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/cities").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/categories").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/bookings").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/bookings/user/*", "/bookings/user/**").hasRole("USER")
                .antMatchers("/home",
                        "/travel-bee/**",
                        "/signin",
                        "/signup",
                        "/roles",
                        "/users",
                        "/features",
                        "/products/random",
                        "/products/**",
                        "/cities/**",
                        "/categories/**",

                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**").permitAll()
                .antMatchers("/roles/**",
                        "/products/admin/**",
                        "/bookings/**",
                        "/bookings/",
                        "/roles",
                        "/roles/*",
                        "/roles/**",
                        "/users",
                        "/users/*",
                        "/users/**",
                        "/products/*").hasRole("ADMIN")
                .antMatchers( "/favorites/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()


                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //se configura el metodo de encriptacion
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsServiceImpl);
        return provider;
    }


}

