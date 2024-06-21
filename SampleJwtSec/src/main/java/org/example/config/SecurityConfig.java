package org.example.config;


import org.example.filter.JwtFilter;
import org.example.repository.UserRepository;
import org.example.service.UserDetailUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtFilter jwtFilter;
    @Bean
    public UserDetailsService userDetailsService(){
        /*UserDetails adminUser = User.withUsername("admin")
                .password(bCryptPasswordEncoder().encode("admin123"))
                .roles("ADMIN").build();
        UserDetails normalUser1 = User.withUsername("saurabh")
                .password(bCryptPasswordEncoder().encode("saurabh123"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(adminUser, normalUser1);*/
        return new UserDetailUserInfo(userRepository);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/products/**")
                        .hasRole("ADMIN")
                        .requestMatchers("/products")
                        .hasRole("USER")
                        .requestMatchers(HttpMethod.POST,"/createUser","/generateToken").permitAll()
                        .requestMatchers("/welcome").permitAll()
                ).csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

                //.formLogin(Customizer.withDefaults()).build();
                //.authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.POST,"/createUser").permitAll())
                //.authorizeHttpRequests(authorize -> authorize.requestMatchers("/welcome").permitAll())
                //.formLogin(Customizer.withDefaults()).build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
     return new BCryptPasswordEncoder();
    }

}
